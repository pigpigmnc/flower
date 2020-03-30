package com.zsc.flower.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class OrderDetail {
    OrderOtherDetail orderOtherDetail;
    List<OrderItemDetail> orderItemDetailList;
}
