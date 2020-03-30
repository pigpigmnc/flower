package com.zsc.flower.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zsc.flower.domain.entity.WebCts;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseImageUrl implements Serializable {
    private String msg;    //执行成功或者失败的消息
    private Object data;
    private String filename;
    private String fileurlpath;
    private ResponseImageUrl(String msg) {
        this.msg = msg;
    }
    private ResponseImageUrl(String msg,String filename,String fileurlpath) {
        this.msg = msg;
        this.filename = filename;
        this.fileurlpath = fileurlpath;
    }
    //返回成功的消息
    public static  ResponseImageUrl createBySuccess() {
        return new ResponseImageUrl(WebCts.RESP_SUCCESS);
    }
    public static ResponseImageUrl createBySuccess(String msg,String filename,String fileurlpath){
        return new ResponseImageUrl(msg,filename,fileurlpath);
    }
    //返回失败的消息
    public static ResponseImageUrl createByError() {
        return new ResponseImageUrl(WebCts.RESP_FAIL);
    }
}
