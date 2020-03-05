package xiong.o2o.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import sun.rmi.runtime.Log;

import java.util.Date;

@Data
public class Inventory {
    @TableId(type = IdType.AUTO)
    Long id;

    Long commodityId;
    Integer quantity;
    private Date createTime;
    private Date updateTime;
}
