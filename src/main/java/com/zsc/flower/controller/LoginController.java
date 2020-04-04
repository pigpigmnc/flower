package com.zsc.flower.controller;

import com.zsc.flower.domain.entity.Users;
import com.zsc.flower.domain.vo.ResponseLogin;
import com.zsc.flower.service.Impl.TokenService;
import com.zsc.flower.service.Impl.UsersServiceImpl;
import com.zsc.flower.service.util.JwtConstants;
import com.zsc.flower.service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping(value = "/entrance")
public class LoginController {
    @Autowired
    UsersServiceImpl usersService;
    @Autowired
    TokenService tokenService;
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseLogin login(@RequestParam String username, @RequestParam String password) {
        //根据用户名查找账号
        Users user = usersService.findUser(username,password);

        if(user==null)
        {
            ResponseLogin responseLogin=ResponseLogin.createByError();
            responseLogin.setMsg("用户名密码错误");
            return responseLogin;//为空就直接拦截 返回给前端msg="fail"}
        }
        else
        {
            if(user.getStatus()==0)
            {
                ResponseLogin responseLogin=ResponseLogin.createByError();
                responseLogin.setMsg("该用户已被停用");
                return responseLogin;//为空就直接拦截 返回给前端msg="fail"}
            }
            else {
                try {
                    JwtUtil util = new JwtUtil();
                    String jwt = util.createJWT(String.valueOf(user.getId()), "Shoppingmall", user, JwtConstants.EXPIRATION );
                    tokenService.saveToken(user,JwtConstants.EXPIRATION ,jwt);
                    String name=user.getUsername();
                    ResponseLogin responseLogin=ResponseLogin.createBySuccess(user.getRole(),user.getStatus());
                    responseLogin.setData(name);
                    responseLogin.setToken(jwt);
                    return responseLogin;
                } catch (Exception e) {
                    e.printStackTrace();
                    ResponseLogin responseLogin=ResponseLogin.createByError();
                    responseLogin.setMsg("系统出现错误!");
                    return responseLogin;//为空就直接拦截 返回给前端msg="fail"}
                }
            }

        }
       /* if(!password.equals(user.getPassword()))return ResponseLogin.createByError();//密码错误直接拦截 返回给前端msg="fail"
        if(user.getStatus()==0)return ResponseLogin.createByError(0);//账号处于停用状态 返回给前端status=0
        if (user.getRole() == WebCts.ROLE_ADMIN) {//没被拦截的是成功的，直接判断角色
            return ResponseLogin.createBySuccess(0,1);//返回给前端msg="succes",role=0,status=1
        }else{
            return ResponseLogin.createBySuccess(1,1);//返回给前端msg="succes",role=1,status=1
        }*/
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseLogin logout(HttpServletRequest request)
    {
        tokenService.del(tokenService.reUserIdBytoken(request.getHeader("token")));
        ResponseLogin responseLogin=ResponseLogin.createBySuccess();
        responseLogin.setMsg("登出成功");
        return responseLogin;
    }
    @RequestMapping(value = "/checkoutusername", method = RequestMethod.GET)
    public ResponseLogin checkoutusername(@RequestParam("username")String username)
    {
        Users users=usersService.findUserByUsername(username);
        if(users==null)
        {
            ResponseLogin responseLogin=ResponseLogin.createBySuccess(0,1);
            responseLogin.setMsg("yes");
            return  responseLogin;
        }
        else
        {
            ResponseLogin responseLogin=ResponseLogin.createByError(0,1);
            responseLogin.setMsg("no");
            return  responseLogin;
        }

    }
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ResponseLogin register(@RequestParam("username")String username, @RequestParam("password")String password,
                                  @RequestParam("phone")String phone, @RequestParam("email")String email)
    {
        try{
            Users users=usersService.findUserByUsername(username);
            if(users==null)
            {
                Users user=new Users();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.setPhone(phone);
                user.setRole(1);
                user.setStatus(1);
                user.setRegisterDate(new Date());
                usersService.findAddUser(user);
                ResponseLogin responseLogin=ResponseLogin.createBySuccess();
                responseLogin.setMsg("ok");
                return  responseLogin;
            }
            else
            {
                ResponseLogin responseLogin=ResponseLogin.createByError();
                responseLogin.setMsg("注册失败");
                return  responseLogin;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ResponseLogin responseLogin=ResponseLogin.createByError();
            responseLogin.setMsg("系统出现错误!");
            return responseLogin;//为空就直接拦截 返回给前端msg="fail"}
        }
    }
    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
    public ResponseLogin checkLogin(HttpServletRequest request){
        String token = request.getHeader("token");
        if(token==null){
            return ResponseLogin.createByError();
        }
        else{
            return ResponseLogin.createBySuccess();
        }
    }

}
