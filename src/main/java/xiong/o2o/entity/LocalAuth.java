package xiong.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class LocalAuth {
    @Getter @Setter
    private Long localAuthId;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private Date createTime;

    @Getter @Setter
    private Date lastEditTime;

    @Getter @Setter
    private PersonInfo personInfo;
}
