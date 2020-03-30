package com.zsc.flower.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zsc.flower.domain.entity.WebCts;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseLogin implements Serializable {
    private int status;
    private int role;
    private String msg;    //执行成功或者失败的消息
    private Object data;         //返回给前端的数据
    private String token;
    private ResponseLogin(String msg) {
        this.msg = msg;
    }
    private ResponseLogin(String msg,int role,int status) {
        this.msg = msg;
        this.role=role;
        this.status=status;
    }
    private ResponseLogin(String msg,int status) {
        this.msg = msg;
        this.status=status;
    }
    //返回成功的消息
    public static  ResponseLogin createBySuccess() {
        return new ResponseLogin(WebCts.RESP_SUCCESS);
    }
    //返回失败的消息
    public static ResponseLogin createByError() {
        return new ResponseLogin(WebCts.RESP_FAIL);
    }
    //返回成功的信息
    public static  ResponseLogin createBySuccess(int role,int status) {
        return new ResponseLogin(WebCts.RESP_SUCCESS,role,status);
    }
    //返回失败的消息
    public static ResponseLogin createByError(int role,int status) {
        return new ResponseLogin(WebCts.RESP_FAIL,role,status);
    }
    public static ResponseLogin createByError(int status) {
        return new ResponseLogin(WebCts.RESP_FAIL,status);
    }
}
