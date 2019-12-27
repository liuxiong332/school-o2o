package com.orangemako.spring.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Shop {
    @Getter @Setter
    private Long shopId;

    @Getter @Setter
    private String shopName;

    @Getter @Setter
    private String shopDesc;

    @Getter @Setter
    private String shopAddr;

    @Getter @Setter
    private String phone;

    @Getter @Setter
    private String shopImg;

    @Getter @Setter
    private Integer priority;

    @Getter @Setter
    private Date createTime;

    @Getter @Setter
    private Date lastEditTime;

    @Getter @Setter
    private Integer enableStatus; // -1 不可用 0 审核中 1 可用

    @Getter @Setter
    private String advice;  // 管理员给店家的提醒

    @Getter @Setter
    private Area area;

    @Getter @Setter
    private PersonInfo owner;

    @Getter @Setter
    private ShopCategory shopCategory;
}
