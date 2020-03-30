package com.zsc.flower.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OrderOtherDetail {
    String orderCode;
    Float orderPrice;
    String receiver;
    String mobile;
    String address;
    String userMessage;
    Date createDate;
}
