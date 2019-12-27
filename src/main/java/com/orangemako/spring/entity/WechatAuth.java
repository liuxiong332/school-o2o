package com.orangemako.spring.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class WechatAuth {
    @Getter @Setter
    private Long wechatAuthId;

    @Getter @Setter
    private String openId;

    @Getter @Setter
    private Date createTime;

    @Getter @Setter
    private PersonInfo personInfo;
}
