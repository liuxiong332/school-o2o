package xiong.o2o.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class Commodity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Float price;
    private Date createTime;
    private Date updateTime;
}
