package xiong.o2o.mapper;

import org.apache.ibatis.jdbc.SQL;

public class InventorySqlBuilder {
    public static String buildDecreaseQuantitySql(Long commodityId, Integer quantity) {
        return new SQL(){{
            UPDATE("inventory");
            SET("quantity = quantity - #{quantity}");
            WHERE("commodity_id = #{commodityId} AND quantity >= #{quantity}");
        }}.toString();
    }
}