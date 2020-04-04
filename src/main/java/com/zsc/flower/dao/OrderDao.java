package com.zsc.flower.dao;

import com.zsc.flower.domain.entity.OrderItemDetail;
import com.zsc.flower.domain.entity.OrderOtherDetail;
import com.zsc.flower.domain.entity.Orderitem;
import com.zsc.flower.domain.entity.Orders;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends BaseMapper<Orders> {
    public int selectAddOrder(Orders order);
    public int selectAddOrderItem(Orderitem orderitem);
    public long selectNewOrderId();
    public float selectOrderPrice(@Param("oid") long oid);
    public int selectUpdateOrderPrice(@Param("orderPrice") float orderPrice, @Param("id") long id);
    public List<Orders> selectListOrderByUid(@Param("uid") long uid);
    public List<Orders> selectListAllOrder();
    public List<OrderItemDetail> selectOrderItemDetail(@Param("uid") long uid, @Param("id") long id);
    public OrderOtherDetail selectOrderOtherDetail(@Param("uid") long uid, @Param("id") long id);


    public Orders selectOrderByOrderCode(@Param("orderCode") String orderCode);
    public int selectUpdatePayDate(Orders order);

    public Orders selectOrderByUIdAndCode(@Param("orderCode") String orderCode);
    public int selectUpdateOrderStatus(@Param("status") String status,
                                       @Param("confirmDate") String confirmDate,
                                       @Param("orderCode") String orderCode);
    public int selectUpdateOrder(Orders order);

    public List<Orders> selectOrderByStatus(@Param("status") String status);

    public void selectDeleteOrderById(@Param("id") long id);

    //=====================
    public void deletOrderByStatusAndUid(@Param("id") long id, @Param("uid") long uid, @Param("status") String status);
    public Orders selectOrderById(@Param("id") long id);
    public int selectUpdateOrders(Orders order);

    List<OrderItemDetail> findOrderItemDetailById(long orderId);
}
