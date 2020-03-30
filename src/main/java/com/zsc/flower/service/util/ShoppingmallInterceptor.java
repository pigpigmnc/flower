package com.zsc.flower.service.util;

import com.zsc.flower.domain.vo.ResponseData;
import com.zsc.flower.service.Impl.TokenService;
import com.zsc.flower.service.Impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@CrossOrigin
@Component("shoppingmallInterceptor")
public class ShoppingmallInterceptor implements HandlerInterceptor {
    @Autowired
    TokenService tokenService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UsersServiceImpl usersService;
    @Autowired
    JsonUtil1 jsonUtil1;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt=request.getHeader("token");
        System.out.println(jwt);
        if(!tokenService.existsToken(jwt)){
            return reLogin(request,response);
        }

        return true;
    }

    private boolean reLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out;
        try{
            ResponseData responseData = ResponseData.createByError();
            responseData.setStatus("444");
            String json = JsonUtil1.obj2String(responseData);
            response.getWriter().write(json);
            return false;
        } catch (Exception e){
            e.printStackTrace();
            ResponseData responseData = ResponseData.createByError();
            responseData.setMsg("系统出错");
            String json = JsonUtil1.obj2String(responseData);
            response.getWriter().write(json);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}
