package com.zsc.flower.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.flower.domain.entity.*;
import com.zsc.flower.domain.vo.ResponseData;
import com.zsc.flower.domain.vo.ResponseDataPay;
import com.zsc.flower.service.CartService;
import com.zsc.flower.service.Impl.TokenService;
import com.zsc.flower.service.OrderService;
import com.zsc.flower.service.ProductService;
import com.zsc.flower.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;
    //增加对库存和销量的处理2019-7-12 11:27
    @Autowired
    ProductService productService;
    @Autowired
    UsersService usersService;
    @Autowired
    TokenService tokenService;

    //这里相当于从购物车里选中商品，然后结算生成订单，然后生成订单项
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public ResponseDataPay addOrder(HttpServletRequest request, OrderAttach orderAttach) {
        //用户可以修改和提交的内容有：地址，收货人名字，收货人电话，买家留言，都是order表的字段
        //新建这个订单
        Orders order = new Orders();
        String token = request.getHeader("token");
        long uid = Long.parseLong(tokenService.reUserIdBytoken(token));
        order.setUid(uid);
        order.setAddress(orderAttach.getAddress());
        order.setReceiver(orderAttach.getReceiver());
        order.setMobile(orderAttach.getMobile());
        order.setUserMessage(orderAttach.getUserMessage());

        order.setOrderCode(String.valueOf(System.currentTimeMillis()));
        Date date = new Date();
        order.setCreateDate(date);
        order.setStatus("待付款");

        int n = orderService.findAddOrder(order);//此处成功插入orders表中


        long orderid = orderService.findNewOrderId();

//        接下来要做的是插入该订单的订单项

        List<Long> pidList = orderAttach.getPid();//这里两个36

        Orderitem orderitem = new Orderitem();
        int m = 0, a = 0;
        int orderPrice = 0;
        for (long pid : pidList) {//从多选框里把选中的pid遍历出来，然后跟orderitem绑在一起
            Cart cart = cartService.findCartByUidAndPid(uid, pid);
            orderitem.setPid(pid);
            orderitem.setOid(orderid);
            orderitem.setUid(uid);
            orderitem.setNumber((long) cart.getCount());
            orderitem.setSimplePrice(cart.getSimplePrice());
            orderitem.setTotalPrice(cart.getTotalPrice());

            m = orderService.findAddOrderItem(orderitem);//逐项插入订单项

            //增加对库存和销量的处理2019-7-12 11:27
            if (m == 1) {
                //库存
                long oldStock = productService.findOldStock(pid);
                productService.findUpdateStock(pid, oldStock - cart.getCount());
                long oldSaleCount = productService.findOldSaleCount(pid);
                productService.findUpdateSaleCount(pid, oldSaleCount + cart.getCount());
            }
            //删除对应的购物车！！！！！！！！！！！！
            cartService.findDeleteCartByUidAndPid(uid, pid);
        }
        if (n == 1 && m == 1) {
            orderPrice = (int) orderService.findOrderPrice(orderid);
            a = orderService.findUpdateOrderPrice(orderPrice, orderid);
        }
        if (a == 1) {
//            checkPayStation(String.valueOf(System.currentTimeMillis()));//把订单编号传入这个函数里
//                return ResponseDataPay.createBySuccess(WebCts.RESP_SUCCESS,String.valueOf(System.currentTimeMillis()),
//                        String.valueOf(System.currentTimeMillis()), String.valueOf(orderPrice));
            return ResponseDataPay.createBySuccess(WebCts.RESP_SUCCESS, uid, orderid, String.valueOf(order.getOrderCode()),
                    String.valueOf(order.getOrderCode()), String.valueOf(orderPrice));

        } else
            return ResponseDataPay.createByError();
    }

    //展示用户的订单列表,所有订单
    @RequestMapping(value = "/listOrderByUid", method = RequestMethod.GET)
    public ResponseData listOrderByUid(@RequestParam(defaultValue = "1", required = true, value = "pn") Integer pn, HttpServletRequest request) {
        int pageSize = 10;
        PageHelper.startPage(pn, pageSize);
        String token = request.getHeader("token");
        long uid = Long.parseLong(tokenService.reUserIdBytoken(token));
        PageInfo<Orders> pageInfo = orderService.findListOrderByUid(pn, pageSize, uid);
        ResponseData responseData = ResponseData.createBySuccess();
        responseData.setData(pageInfo);
        return responseData;
    }

    //后台订单列表
    @RequestMapping(value = "/listAllOrder", method = RequestMethod.GET)
    public ResponseData listAllOrder(@RequestParam(defaultValue = "1", required = true, value = "pn") Integer pn) {
        int pageSize = 10;
        PageHelper.startPage(pn, pageSize);
        PageInfo<Orders> pageInfo = orderService.findListAllOrder(pn, pageSize);
        ResponseData responseData = ResponseData.createBySuccess();
        responseData.setData(pageInfo);
        return responseData;
    }

    //用户订单项的显示
    @RequestMapping(value = "/OrderDetail", method = RequestMethod.GET)
    public ResponseData listOrderAndDetail(HttpServletRequest request,
                                           @RequestParam("orderCode") String orderCode) {
        String token = request.getHeader("token");
        OrderDetail orderDetail = new OrderDetail();
        long uid = Long.parseLong(tokenService.reUserIdBytoken(token));
        if (!orderCode.equals("")) {
            Orders orders = orderService.findOrderByOrderCode(orderCode);
            long id = orders.getId();
            List<OrderItemDetail> orderItemDetailList;
            OrderOtherDetail orderOtherDetail;
            orderItemDetailList = orderService.findOrderItemDetail(uid, id);
            orderOtherDetail = orderService.findOrderOtherDetail(uid, id);
            orderDetail.setOrderItemDetailList(orderItemDetailList);
            orderDetail.setOrderOtherDetail(orderOtherDetail);
        } else {
            //此处针对用户直接把购物车里的东西全部结算
            List<Cart> cartList = cartService.findListCartByUid(uid);
            Users user = usersService.findUserById(uid);
            Orders order = new Orders();
            order.setUid(uid);
            order.setAddress(user.getAddress());
            order.setReceiver(user.getUsername());
            order.setMobile(user.getPhone());
            order.setOrderCode(String.valueOf(System.currentTimeMillis()));
            Date date = new Date();
            order.setCreateDate(date);
            order.setStatus("待付款");
            orderService.findAddOrder(order);//此处成功插入orders表中
            long orderId = orderService.findNewOrderId();
            //插入该订单的订单项
            List<Long> pidList = new ArrayList<>();
            cartList.forEach(cart -> {
                pidList.add(cart.getPid());
            });
            Orderitem orderitem = new Orderitem();
            float orderPrice = 0;
            for (long pid : pidList) {//从多选框里把选中的pid遍历出来，然后跟orderitem绑在一起
                for (Cart cart : cartList) {
                    if (cart.getPid().equals(pid)) {
                        orderitem.setPid(pid);
                        orderitem.setOid(orderId);
                        orderitem.setUid(uid);
                        orderitem.setNumber((long) cart.getCount());
                        orderitem.setSimplePrice(cart.getSimplePrice());
                        orderitem.setTotalPrice(cart.getTotalPrice());
                        orderPrice = orderPrice + orderitem.getTotalPrice();
                        orderService.findAddOrderItem(orderitem);//逐项插入订单项
                        long oldStock = productService.findOldStock(pid);
                        productService.findUpdateStock(pid, oldStock - cart.getCount());
                        long oldSaleCount = productService.findOldSaleCount(pid);
                        productService.findUpdateSaleCount(pid, oldSaleCount + cart.getCount());
                        cartService.findDeleteCartByUidAndPid(uid, pid);
                    }
                }
            }
            List<OrderItemDetail> orderItemDetailList = orderService.findOrderItemDetail(uid, orderId);
            OrderOtherDetail orderOtherDetail = orderService.findOrderOtherDetail(uid, orderId);
            orderService.findUpdateOrderPrice(orderPrice, orderId);
            orderDetail.setOrderItemDetailList(orderItemDetailList);
            orderDetail.setOrderOtherDetail(orderOtherDetail);

        }
        ResponseData responseData = new ResponseData("success");
        responseData.setData(orderDetail);

        return responseData;
    }

    @RequestMapping(value = "/getOrderDetailByAdmin", method = RequestMethod.GET)
    public ResponseData getOrderDetailByAdmin(@RequestParam("orderId")long orderId) {
        List<OrderItemDetail> orderItemDetailList = orderService.findOrderItemDetailById(orderId);
        ResponseData responseData = new ResponseData("success");
        responseData.setData(orderItemDetailList);
        return responseData;
    }


    //用户自己修改订单状态，将待收货修改为确认收货
    @RequestMapping(value = "/getProduct", method = RequestMethod.GET)
    public ResponseData getProduct(@RequestParam("orderCode") String orderCode) {
        Orders order = orderService.findOrderByUIdAndCode(orderCode);
        if (order.getStatus() == "已发货") {
            order.setConfirmDate(new Date());
            if (orderService.findUpdateOrderStatus("已确认收货",
                    new Date().toLocaleString(), orderCode) == 1)
                return ResponseData.createBySuccess();
            else
                return ResponseData.createByError();
        } else {
            ResponseData responseData = new ResponseData("你的商品还没发货呢");
            responseData.setStatus("fail");
            return responseData;
        }
    }

    //后台订单状态修改，改为已发货
    @RequestMapping(value = "/postProduct", method = RequestMethod.GET)
    public ResponseData postProduct(@RequestParam("orderCode") String orderCode,
                                    @RequestParam("post") String post) {
        Orders order = orderService.findOrderByOrderCode(orderCode);
        order.setStatus("已发货");
        order.setPost(post);
        order.setDeliveryDate(new Date());
        if (orderService.findUpdateOrder(order) == 1)
            return ResponseData.createBySuccess();
        else
            return ResponseData.createByError();
    }

    //立即购买商品
    @RequestMapping(value = "/buyNow", method = RequestMethod.GET)
    public ResponseDataPay buyNow(BuyNow buyNow, HttpServletRequest request) {
        //用户可以修改和提交的内容有：地址，收货人名字，收货人电话，买家留言，都是order表的字段
        //新建这个订单
        Orders order = new Orders();
        String token = request.getHeader("token");
        long uid = Long.parseLong(tokenService.reUserIdBytoken(token));
        order.setUid(uid);
        order.setAddress(buyNow.getAddress());
        order.setReceiver(buyNow.getReceiver());
        order.setMobile(buyNow.getMobile());
        order.setUserMessage(buyNow.getUserMessage());

        order.setOrderCode(String.valueOf(System.currentTimeMillis()));
        Date date = new Date();
        order.setCreateDate(date);
        order.setStatus("待付款");

        int n = orderService.findAddOrder(order);//此处成功插入orders表中
        long orderid = orderService.findNewOrderId();

//        接下来要做的是插入该订单的订单项

        Orderitem orderitem = new Orderitem();
        int m = 0, a = 0;
        int orderPrice = 0;
        orderitem.setPid(buyNow.getPid());
        orderitem.setOid(orderid);
        orderitem.setUid(uid);
        orderitem.setNumber(buyNow.getNumber());

        Product product = productService.findProductById(buyNow.getPid());


        orderitem.setSimplePrice(product.getPromotePrice());
        orderitem.setTotalPrice(buyNow.getNumber() * product.getPromotePrice());
        m = orderService.findAddOrderItem(orderitem);//逐项插入订单项

        //增加对库存和销量的处理2019-7-12 11:27
        if (m == 1) {
            //库存
            long oldStock = productService.findOldStock(buyNow.getPid());
            productService.findUpdateStock(buyNow.getPid(), oldStock - buyNow.getNumber());
            long oldSaleCount = productService.findOldSaleCount(buyNow.getPid());
            productService.findUpdateSaleCount(buyNow.getPid(), oldSaleCount + buyNow.getNumber());
        }
//        }
        if (n == 1 && m == 1) {
            orderPrice = (int) orderService.findOrderPrice(orderid);
            a = orderService.findUpdateOrderPrice(orderPrice, orderid);
        }
        if (a == 1) {
            return ResponseDataPay.createBySuccess(WebCts.RESP_SUCCESS, uid, orderid, String.valueOf(order.getOrderCode()),
                    String.valueOf(order.getOrderCode()), String.valueOf(orderPrice));
        } else
            return ResponseDataPay.createByError();
    }

    //按订单编号查找订单信息
    @RequestMapping(value = "/findOrderByOrderId", method = RequestMethod.GET)
    public ResponseData findOrderByOrderId(@RequestParam("orderCode") String orderCode) {
        Orders order = orderService.findOrderByOrderCode(orderCode);
        ResponseData responseData = ResponseData.createBySuccess();
        responseData.setData(order);
        return responseData;
    }

    //==========================================
    @RequestMapping(value = "/delOrder", method = RequestMethod.GET)
    public ResponseData delOrder(@RequestParam("id") long id, HttpServletRequest request) {
        try {
            String jwt = request.getHeader("token");
            Users users = usersService.findUserById(Long.parseLong(tokenService.reUserIdBytoken(jwt)));
            orderService.findDelByIdAndUid(id, users.getId());
            ResponseData responseData = ResponseData.createBySuccess();
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData responseData = ResponseData.createByError();
            responseData.setMsg("删除失败，系统错误");
            return responseData;
        }
    }

    @RequestMapping(value = "/alterOrder", method = RequestMethod.POST)
    public ResponseDataPay updateOrder(@RequestParam("receiver") String receiver, @RequestParam("phone") String phone,
                                       @RequestParam("address") String address, @RequestParam("msg") String msg, @RequestParam("ordercode") String ordercode, HttpServletRequest request) {
        Orders order = orderService.findOrderByOrderCode(ordercode);
        if (order == null) {
            ResponseDataPay responseData = ResponseDataPay.createByError();
            responseData.setMsg("没有此订单");
            return responseData;
        } else {
            order.setAddress(address);
            order.setReceiver(receiver);
            order.setMobile(phone);
            order.setUserMessage(msg);
            order.setStatus("待付款");
            int n = orderService.findUpdatas(order);
            ResponseDataPay responseData = ResponseDataPay.createBySuccess();
            responseData.setOrderNum(order.getOrderCode());
            responseData.setProductName(order.getOrderCode());
            responseData.setPrice(String.valueOf(order.getOrderPrice()));
            return responseData;
        }
    }
}
