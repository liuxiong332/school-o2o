package xiong.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class ProductImg {
    @Getter @Setter
    private Long productImgId;

    @Getter @Setter
    private String imgAddr;

    @Getter @Setter
    private String imgDesc;

    @Getter @Setter
    private Integer priority;

    @Getter @Setter
    private Date createTime;

    @Getter @Setter
    private Long productId;
}
