package com.zsc.flower.service;

import com.github.pagehelper.PageInfo;
import com.zsc.flower.domain.entity.OrderItemDetail;
import com.zsc.flower.domain.entity.OrderOtherDetail;
import com.zsc.flower.domain.entity.Orderitem;
import com.zsc.flower.domain.entity.Orders;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    public int findAddOrder(Orders order);
    public int findAddOrderItem(Orderitem orderitem);
    public long findNewOrderId();
    public float findOrderPrice(@Param("oid") long oid);
    public int findUpdateOrderPrice(float orderPrice, long id);
    //==========================
    public PageInfo<Orders> findListOrderByUid(int page, int size, @Param("uid") long uid);

    public PageInfo<Orders> findListAllOrder(int page, int size);

    public List<OrderItemDetail> findOrderItemDetail(long uid, long id);

    public OrderOtherDetail findOrderOtherDetail(long uid, long id);

    public Orders findOrderByOrderCode(String orderCode);
    public int findUpdatePayDate(Orders order);

    public Orders findOrderByUIdAndCode(String orderCode);
    public int findUpdateOrderStatus(String status,
                                     String confirmDate,
                                     String orderCode);
    public int findUpdateOrder(Orders order);

    public List<Orders> findOrderByStatus(String status);

    public void findDeleteOrderById(long id);
//===========================================
    public void findDelByIdAndUid(long id, long uid);

    public Orders findOrderById(long id);

    public int findUpdatas(Orders orders);
}
