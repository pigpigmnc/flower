package com.zsc.flower.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ListProduct {
    Long id;
    String name;
    String subTitle;
    Float originalPrice;
    Float promotePrice;
    Long stock;
    Date createDate;
    String fileurlpath;
//    List<String> fileurlpath;
    String cname;
    Long saleCount;
}
