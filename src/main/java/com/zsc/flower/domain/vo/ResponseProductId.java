package com.zsc.flower.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zsc.flower.domain.entity.WebCts;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseProductId implements Serializable {
    private String msg;    //执行成功或者失败的消息
    private Object data;
    private long pId;
    private ResponseProductId(String msg) {
        this.msg = msg;
    }
    private ResponseProductId(String msg,long pId) {
        this.msg = msg;
        this.pId=pId;
    }
    //返回成功的消息
    public static  ResponseProductId createBySuccess() {
        return new ResponseProductId(WebCts.RESP_SUCCESS);
    }
    public static ResponseProductId createBySuccess(String msg,long pId){
        return new ResponseProductId(msg,pId);
    }
    //返回失败的消息
    public static ResponseProductId createByError() {
        return new ResponseProductId(WebCts.RESP_FAIL);
    }
}
