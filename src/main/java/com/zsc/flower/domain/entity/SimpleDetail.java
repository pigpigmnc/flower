package com.zsc.flower.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SimpleDetail {
    Long id;//商品的ID
    String name;//商品名
    String subTitle;//二级标题varchar(5000)
    Float originalPrice;//商品原价
    Float promotePrice;//商品促销价
    Long stock;//库存
    Long saleCount;//销量
    Date createDate;
    String categoryname;
}
