package com.zsc.flower.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zsc.flower.domain.entity.WebCts;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseData implements Serializable {
    private String msg;    //执行成功或者失败的消息
    private Object data;         //返回给前端的数据
    private String status;
    public ResponseData(String msg) {
        this.msg = msg;
    }
    //返回成功的消息
    public static  ResponseData createBySuccess() {
        return new ResponseData(WebCts.RESP_SUCCESS);
    }
    //返回失败的消息
    public static ResponseData createByError() {
        return new ResponseData(WebCts.RESP_FAIL);
    }
}
