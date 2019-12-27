package com.orangemako.spring.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class ShopCategory {
    @Getter
    @Setter
    private Long shopCategoryId;

    @Getter
    @Setter
    private Long shopCategoryName;

    @Getter @Setter
    private String shopCategoryDesc;

    @Getter @Setter
    private String shopCategoryImg;

    @Getter @Setter
    private Integer priority;

    @Getter @Setter
    private Date createTime;

    @Getter @Setter
    private Date lastEditTime;

    @Getter @Setter
    private ShopCategory parent;
}
