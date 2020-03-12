package xiong.o2o.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import javafx.util.Pair;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xiong.o2o.entity.*;
import xiong.o2o.exception.InvalidParamException;
import xiong.o2o.exception.UnauthorizatedException;
import xiong.o2o.mapper.*;
import xiong.o2o.shiro.JWTUtil;
import xiong.o2o.vo.CommodityVO;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    String genCommodityRedisKey(Long itemId) {
        return "commodity." + itemId;
    }

    @Transactional
    public List<CommodityVO> getAllCommodities() {

        // Get cache from redis
        if (redisTemplate.hasKey("commodityIds")) {
            List<Long> ids = (List<Long>)redisTemplate.opsForValue().get("commodityIds");
            List<String> keys = ids.stream().map(s -> "commodity." + s).collect(Collectors.toList());
            return (List<CommodityVO>) redisTemplate.opsForValue().multiGet(keys);
        }

        List<Commodity> commodities = commodityMapper.selectAll();
        List<Long> ids = commodities.stream().map(c -> c.getId()).collect(Collectors.toList());

        List<Inventory> inventories = Collections.emptyList();
        if (!ids.isEmpty()) {
            QueryWrapper query = new QueryWrapper();
            query.in("commodity_id", ids);
            inventories = inventoryMapper.selectList(query);
        }


        List<CommodityVO> results = Streams.zip(commodities.stream(), inventories.stream(), (x, y) -> CommodityVO.fromCI(x, y))
            .collect(Collectors.toList());

        Map<String, CommodityVO> keys = Streams
            .zip(ids.stream(), results.stream(), (k, v) -> new Pair<String, CommodityVO>("commodity." + k, v))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        redisTemplate.opsForValue().set("commodityIds", ids);
        redisTemplate.opsForValue().multiSet(keys);

        return results;
    }

    @Transactional
    public CommodityVO getCommodity(Long id) {

        // 先从Redis中取缓存
        if (redisTemplate.hasKey("commodity." + id)) {
            return(CommodityVO)redisTemplate.opsForValue().get("commodity." + id);
        }
        Commodity commodity = commodityMapper.selectById(id);

        QueryWrapper<Inventory> query = new QueryWrapper<>();
        query.eq("commodity_id", id);
        Inventory inventory = inventoryMapper.selectOne(query);

        QueryWrapper<Seckill> seckillQuery = new QueryWrapper<>();
        seckillQuery.eq("commodity_id", id);
        Seckill seckill = seckillMapper.selectOne(seckillQuery);
        CommodityVO commodityVO = CommodityVO.fromCI(commodity, inventory, seckill);

        redisTemplate.opsForValue().set("commodity." + id, commodityVO);
        return commodityVO;
    }

    @Transactional
    public void createCommodity(CommodityVO commodityVO) {
        Commodity commodity = new Commodity();
        // BeanUtils.copyProperties(commodityVO, commodity);
        commodity.setName(commodityVO.getName());
        commodity.setDescription(commodityVO.getDescription());
        commodity.setPrice(commodityVO.getPrice());
        commodityMapper.insert(commodity);

        Inventory inventory = new Inventory();
        // BeanUtils.copyProperties(commodityVO, inventory);
        inventory.setQuantity(commodityVO.getQuantity());
        inventory.setCommodityId(commodity.getId());
        inventoryMapper.insert(inventory);
    }

    @Transactional
    public void updateCommodity(CommodityVO commodityVO) {
        // 如果更新commodity，则将缓存清空
        redisTemplate.delete("commodity." + commodityVO.getId());
        Commodity commodity = new Commodity();
        // BeanUtils.copyProperties(commodityVO, commodity);
        commodity.setId(commodityVO.getId());
        commodity.setName(commodityVO.getName());
        commodity.setDescription(commodityVO.getDescription());
        commodity.setPrice(commodityVO.getPrice());

        Inventory inventory = new Inventory();
        // BeanUtils.copyProperties(commodityVO, inventory);
        inventory.setQuantity(commodityVO.getQuantity());
        inventory.setCommodityId(commodity.getId());
        commodityMapper.updateById(commodity);

        UpdateWrapper update = new UpdateWrapper();
        update.eq("commodity_id", commodity.getId());
        inventoryMapper.update(inventory, update);
    }

    public void createSeckill(Seckill seckill) {
        seckillMapper.insert(seckill);
    }

    public void deleteSeckill(Long seckillId) {
        seckillMapper.deleteById(seckillId);
    }

    @Transactional
    public void placeCommodity(Long commodityId, String token) {
        // 用户是否登录
        String userId = JWTUtil.getUserName(token);
        if (!JWTUtil.verify(token, userId, "123456")) {
            throw new UnauthorizatedException("Token is not valid");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new UnauthorizatedException("User is not valid");
        }

        // 获取商品信息
        Commodity commodity = commodityMapper.selectById(commodityId);
        if (commodity == null) {
            throw new InvalidParamException("The commodity is not exists");
        }

        // QueryWrapper<Inventory> inventoryQuery = new QueryWrapper<>();
        // inventoryQuery.eq("commodity_id", commodity.getId());
        // Inventory inventory = inventoryMapper.selectOne(inventoryQuery);

        // 减库存
        inventoryMapper.decreaseQuantity(commodityId,1);
        // 减库存，会导致commodity缓存失效
        redisTemplate.delete(genCommodityRedisKey(commodityId));
        // 查询是否正在秒杀活动中
        QueryWrapper<Seckill> seckillQuery = new QueryWrapper<>();
        seckillQuery.eq("commodity_id", commodityId);
        Seckill seckill = seckillMapper.selectOne(seckillQuery);

        // 如果正在秒杀中，则取秒杀价格，否则取正常价格
        Float tradePrice = seckill == null ? commodity.getPrice() : seckill.getSeckillPrice();

        Delivery delivery = new Delivery();
        delivery.setCommodityId(commodityId);
        delivery.setPrice(tradePrice);
        delivery.setQuantity(1);
        delivery.setSeckillId(seckill == null ? null : seckill.getId());

        // 新增一个订单
        deliveryMapper.insert(delivery);
    }
}
