package com.orangemako.spring.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class PersonInfo {
    @Getter @Setter
    private Long userId;

    @Setter @Getter
    private String name;

    @Getter @Setter
    private String profileImg;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String gender;

    @Getter @Setter
    private Integer enableStatus;

    // 1 - 顾客 2 - 店家 3 - 超级管理员
    @Getter @Setter
    private Integer userType;

    @Getter @Setter
    private Date createTime;

    @Getter @Setter
    private Date lastEditTime;
}
