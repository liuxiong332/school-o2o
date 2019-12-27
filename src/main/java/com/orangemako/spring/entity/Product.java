package com.orangemako.spring.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class Product {
    @Getter @Setter
    private Long productId;

    @Getter @Setter
    private String productName;

    @Getter @Setter
    private String productDesc;

    @Getter @Setter
    private String imgAddr;

    @Getter @Setter
    private String normalPrice;

    @Getter @Setter
    private String promotionPrice;

    @Getter @Setter
    private Integer priority;

    @Getter @Setter
    private Date createTime;

    @Getter @Setter
    private Date lastEditTime;

    @Getter @Setter
    private Integer enableStatus; // 0 下架 1 可以展示在前端系统

    @Getter @Setter
    private List<ProductImg> productImgList;

    @Getter @Setter
    private ProductCategory productCategory;

    @Getter @Setter
    private Shop shop;
}
