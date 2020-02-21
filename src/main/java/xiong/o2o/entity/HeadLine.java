package xiong.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class HeadLine {
    @Getter @Setter
    private Long lineId;

    @Getter @Setter
    private String lineName;

    @Getter @Setter
    private String lineLink;

    @Getter @Setter
    private String lineImg;

    @Getter @Setter
    private Integer priority;

    @Getter @Setter
    private Integer enableStatus;   // 0: 不可用 1: 可用

    @Getter @Setter
    private Date createTime;

    @Getter @Setter
    private Date lastEditTime;
}
