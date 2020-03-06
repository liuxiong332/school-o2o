package xiong.o2o.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Delivery {

    Long id;
    Long commodityId;
    Long seckillId;
    Integer quantity;
    Float price;
    Date createTime;
    Date updateTime;
}
