package com.orangemako.spring.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Area {
    @Getter @Setter
    private Integer areaId;

    @Getter @Setter
    private String areaName;

    @Getter @Setter
    private Integer priority;

    @Getter @Setter
    private Date crateTime;

    @Getter @Setter
    private Date lastEditTime;
}
