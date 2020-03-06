package xiong.o2o.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xiong.o2o.entity.Commodity;
import xiong.o2o.entity.Inventory;
import xiong.o2o.entity.Seckill;
import xiong.o2o.mapper.CommodityMapper;
import xiong.o2o.mapper.InventoryMapper;
import xiong.o2o.mapper.SeckillMapper;
import xiong.o2o.vo.CommodityVO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommodityService {

    @Autowired
    CommodityMapper commodityMapper;

    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    SeckillMapper seckillMapper;

    @Transactional
    public List<CommodityVO> getAllCommodities() {
        List<Commodity> commodities = commodityMapper.selectAll();
        List<Long> ids = commodities.stream().map(c -> c.getId()).collect(Collectors.toList());

        List<Inventory> inventories = Collections.emptyList();
        if (!ids.isEmpty()) {
            QueryWrapper query = new QueryWrapper();
            query.in("commodity_id", ids);
            inventories = inventoryMapper.selectList(query);
        }

        return Streams.zip(commodities.stream(), inventories.stream(), (x, y) -> CommodityVO.fromCI(x, y))
            .collect(Collectors.toList());
    }

    @Transactional
    public CommodityVO getCommodity(Long id) {
        Commodity commodity = commodityMapper.selectById(id);

        QueryWrapper<Inventory> query = new QueryWrapper<>();
        query.eq("commodity_id", id);
        Inventory inventory = inventoryMapper.selectOne(query);

        QueryWrapper<Seckill> seckillQuery = new QueryWrapper<>();
        seckillQuery.eq("commodity_id", id);
        Seckill seckill = seckillMapper.selectOne(seckillQuery);
        return CommodityVO.fromCI(commodity, inventory, seckill);
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
        update.eq("commodeity_id", commodity.getId());
        inventoryMapper.update(inventory, update);
    }

    public void createSeckill(Seckill seckill) {
        seckillMapper.insert(seckill);
    }

    public void deleteSeckill(Long seckillId) {
        seckillMapper.deleteById(seckillId);
    }
}
