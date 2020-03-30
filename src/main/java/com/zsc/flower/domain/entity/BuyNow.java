package com.zsc.flower.domain.entity;

import lombok.Data;

@Data
public class BuyNow {
    Long uid;
    String address;
    String receiver;
    String mobile;
    String userMessage;
    Long pid;
    Long number;
}
