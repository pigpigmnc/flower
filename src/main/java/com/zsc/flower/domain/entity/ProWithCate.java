package com.zsc.flower.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ProWithCate {
    Long id;
    String name;
    String subTitle;
    Float originalPrice;
    Float promotePrice;
    Long stock;
    Long cid;
    Date createDate;
    String map;
}
