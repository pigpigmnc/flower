package com.zsc.flower.domain.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(generator = "JDBC")
    Long id;
    String orderCode;
    String address;
    String post;
    String receiver;
    String mobile;
    String userMessage;
    Date createDate;
    Date payDate;
    Date deliveryDate;
    Date confirmDate;
    Long uid;
    String status;
    Float orderPrice;
}
