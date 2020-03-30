package com.zsc.flower.domain.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(generator = "JDBC")    //针对Mysql数据用，主键由数据库确定
    Long id;
    String name;
    String subTitle;
    Float originalPrice;
    Float promotePrice;
    Long stock;
    Long cid;
    Date createDate;
    Long saleCount;

}
