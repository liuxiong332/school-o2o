package xiong.o2o.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Seckill {
    @TableId(type = IdType.AUTO)
    Long id;

    Long commodityId;
    Float seckillPrice;
    Date fromTime;
    Date toTime;

    Date createTime;
    Date updateTime;
}
