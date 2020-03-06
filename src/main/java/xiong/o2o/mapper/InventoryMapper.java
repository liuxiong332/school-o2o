package xiong.o2o.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import xiong.o2o.entity.Inventory;

public interface InventoryMapper extends BaseMapper<Inventory> {

    @UpdateProvider(type = InventorySqlBuilder.class, method = "buildDecreaseQuantitySql")
    void decreaseQuantity(Long commodityId, Integer quantity);
}
