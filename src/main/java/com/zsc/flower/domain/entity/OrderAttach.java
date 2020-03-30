package com.zsc.flower.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class OrderAttach {
    Long uid;
    String address;
    String receiver;
    String mobile;
    String userMessage;
    List<Long> pid;
//    Long number;//新增一个属性，是用户提交订单的时候那个商品的数量
}
