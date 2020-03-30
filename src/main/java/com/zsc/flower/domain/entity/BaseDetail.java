package com.zsc.flower.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDetail {
    long id;
    String name;
    String subTitle;
    String originalPrice;
    String promotePrice;
    long stock;
    Date createDate;
    String fileurlpath;
    String cname;
}
