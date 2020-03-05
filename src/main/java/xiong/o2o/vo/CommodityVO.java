package xiong.o2o.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import xiong.o2o.entity.Commodity;
import xiong.o2o.entity.Inventory;

@Data
public class CommodityVO {
    public static CommodityVO fromCI(Commodity commodity, Inventory inventory) {
        CommodityVO commodityVO = new CommodityVO();
        // BeanUtils.copyProperties(commodity, commodityVO);
        // BeanUtils.copyProperties(inventory, commodityVO);
        commodityVO.setId(commodity.getId());
        commodityVO.setName(commodity.getName());
        commodityVO.setDescription(commodity.getDescription());
        commodityVO.setPrice(commodity.getPrice());
        commodityVO.setQuantity(inventory.getQuantity());
        return commodityVO;
    }

    Long id;
    String name;
    String description;
    Float price;
    Integer quantity;
}