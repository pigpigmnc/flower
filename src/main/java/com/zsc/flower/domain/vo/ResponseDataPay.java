package com.zsc.flower.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zsc.flower.domain.entity.WebCts;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseDataPay implements Serializable {
    private String msg;    //执行成功或者失败的消息
    private Object data;
    private String productName;
    private String orderNum;
    private String price;
    private long uid;
    private long id;
    private ResponseDataPay(String msg) {
        this.msg = msg;
    }
    private ResponseDataPay(String msg,long uid,long id,String productName,String orderNum,String price) {
        this.msg = msg;
        this.productName=productName;
        this.orderNum=orderNum;
        this.price=price;
        this.uid=uid;
        this.id=id;
    }
    //返回成功的消息
    public static  ResponseDataPay createBySuccess() {
        return new ResponseDataPay(WebCts.RESP_SUCCESS);
    }
    public static ResponseDataPay createBySuccess(String msg,long uid,long id,String productName,String orderNum,String price){
        return new ResponseDataPay(msg,uid,id,productName,orderNum,price);
    }
    //返回失败的消息
    public static ResponseDataPay createByError() {
        return new ResponseDataPay(WebCts.RESP_FAIL);
    }
}
