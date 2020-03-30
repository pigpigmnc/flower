package com.zsc.flower.service.util;
public interface JwtConstants {
    String AUTH_HEADER = "Authorization";//AUTH_HEADER    是HTTPHeader请求的参数
    String SECRET = "shoppingmall";//SECRET         是具体的加密算法
    String AUTH_PATH = "/attackApi/auth";//AUTH_PATH      是提供给客户端获取JWT参数的接口/需要提供正确的用户名以及密码
    int EXPIRATION = 1000*60*60;//EXPIRATION     是计算JWT过期时间需要用到的
}
