package xiong.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class ProductCategory {
    @Getter @Setter
    private Long productCategoryId;

    @Getter @Setter
    private Long shopId;

    @Getter @Setter
    private String productCategoryName;

    @Getter @Setter
    private Integer priority;

    @Getter @Setter
    private Date createTime;
}
