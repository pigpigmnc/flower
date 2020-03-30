package com.zsc.flower.domain.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(generator = "JDBC")
    Long id;
    Long uid;
    Long pid;
    String fileurlpath;
    String pname;
    float simplePrice;
    int count;
    float totalPrice;
}
