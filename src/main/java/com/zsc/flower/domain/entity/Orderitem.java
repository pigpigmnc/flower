package com.zsc.flower.domain.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "orderitem")
public class Orderitem {
    @Id
    @GeneratedValue(generator = "JDBC")
    Long id;
    Long pid;
    Long oid;
    Long uid;
    Long number;
    Float simplePrice;
    Float totalPrice;
}
