package com.zsc.flower.domain.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="productimage")
public class ProductImage {
    @Id
    @GeneratedValue(generator = "JDBC")    //针对Mysql数据用，主键由数据库确定
    Long id;
    Long pid;
    String filename;
    String fileurlpath;
}
