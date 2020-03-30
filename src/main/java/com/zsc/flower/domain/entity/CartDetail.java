package com.zsc.flower.domain.entity;

import lombok.Data;

@Data
public class CartDetail {
//    @Id
//    @GeneratedValue(generator = "JDBC")
    long id;
    String name;
    Float promotePrice;
    String fileurlpath;
}
