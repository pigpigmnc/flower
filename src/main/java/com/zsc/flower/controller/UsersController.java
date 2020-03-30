package com.zsc.flower.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.flower.domain.entity.Users;
import com.zsc.flower.domain.entity.WebCts;
import com.zsc.flower.domain.vo.ResponseData;
import com.zsc.flower.service.Impl.TokenService;
import com.zsc.flower.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/u")
public class UsersController {
    @Autowired
    UsersService usersService;
    @Autowired
    TokenService tokenService;

    @GetMapping("/getAllUsers")
    public ResponseData getAllUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value="size", defaultValue = "20") int size){
        PageInfo<Users> pageInfo= usersService.findAllUsers(page,size);
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(pageInfo);
        return responseData;
    }
    @PostMapping("/getAddUser")//插入用get响应
    public ResponseData getAddUser(Users user){
        //!!!!!!!!!!!!
        user.setRegisterDate(new Date());
        user.setStatus(WebCts.ACTIVE);//注册默认激活
        user.setRole(WebCts.ROLE_USER);//默认普通用户
        if(usersService.findAddUser(user)==1)
            return ResponseData.createBySuccess(); //插入成功返回msg="success"
        else
            return ResponseData.createByError();
    }
    @GetMapping("/getAll")
    public ResponseData getAll(@RequestParam(defaultValue = "1",required = true,value="pn")Integer pn){
        int pageSize=10;
        PageHelper.startPage(pn,pageSize);
        PageInfo<Users> pageInfo= usersService.findAllUsers(pn,pageSize);
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(pageInfo);
        return responseData;
    }

    @RequestMapping(value = "/getUserById",method = RequestMethod.GET)
    public ResponseData getUserById(long id){
        Users user=usersService.findUserById(id);
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(user);
        return responseData;
    }
    //前台用户个人资料修改
    @RequestMapping(value = "/modifyItself",method = RequestMethod.POST)
    public ResponseData modifyItself(Users user){
        if(usersService.findModifyItself(user)==1)
            return ResponseData.createBySuccess();
        else
            return ResponseData.createByError();
    }
    //后台编辑用户信息
    @RequestMapping(value = "/modifyUser",method = RequestMethod.GET)
    public ResponseData modifyUser(Users user){
        long id=user.getId();
        Users newUser=usersService.findUserById(id);
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setPhone(user.getPhone());
        newUser.setEmail(user.getEmail());
        newUser.setAddress(user.getAddress());
        if(usersService.findModifyItself(newUser)==1)
            return ResponseData.createBySuccess();
        else
            return ResponseData.createByError();
    }


    //激活或者停用用户
    @RequestMapping(value = "/changeUserStatus",method = RequestMethod.GET)
    public ResponseData changeUserStatus(@RequestParam("id")long id){
        Users user=usersService.findUserById(id);
        int status=usersService.findUserStatusById(id);
//        System.out.println(status);
        if(status==0)
            usersService.findChangeUserStatus(id,1);
        switch (status){
            case 1:
                usersService.findChangeUserStatus(id,0);
                break;
            case 0:
                usersService.findChangeUserStatus(id,1);
                break;
            default:
                break;
        }
        return ResponseData.createBySuccess();
    }

    @RequestMapping(value = "/listUserByRegisterDate",method = RequestMethod.GET)
    //根据用户注册时间，分页返回5条数据
    public ResponseData listUserByRegisterDate(@RequestParam(defaultValue = "1",required = true,value="pn")Integer pn){
        int pageSize=5;
        PageHelper.startPage(pn,pageSize);
        PageInfo<Users> pageInfo= usersService.findUserByRegisterDate(pn,pageSize);
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(pageInfo);
        return responseData;
    }
    @PostMapping("/alterPsersonalMsg")
    public ResponseData updataUser(@RequestParam("phone")String phone, @RequestParam("email")String email,
                                   @RequestParam("address")String address, HttpServletRequest request) {
        String jwt=request.getHeader("token");
        Users users=usersService.findUserById(Long.parseLong(tokenService.reUserIdBytoken(jwt)));
        System.out.println("before");
        users.setPhone(phone);
        System.out.println("test");
        users.setEmail(email);
        users.setAddress(address);
        int id=usersService.findModifyItself(users);
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setMsg("修改成功");
        return responseData;

    }
    @PostMapping("/alterPassword")
    public ResponseData updataPassword(@RequestParam("oldPassword")String oldPassword, @RequestParam("newPassword")String newPassword, HttpServletRequest request)
    {
        try{
            String jwt=request.getHeader("token");
            Users users=usersService.findUserById(Long.parseLong(tokenService.reUserIdBytoken(jwt)));
            if(users.getPassword().equals(oldPassword))
            {
                users.setPassword(newPassword);
                int id=usersService.findModifyItself(users);
                ResponseData responseData=ResponseData.createBySuccess();
                responseData.setMsg("修改成功");
                return responseData;
            }
            else
            {
                ResponseData responseData=ResponseData.createByError();
                responseData.setMsg("密码错误");
                return responseData;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ResponseData responseData=ResponseData.createByError();
            responseData.setMsg("修改失败，系统错误!");
            return responseData;
        }
    }
    @PostMapping("/alterUsername")
    public ResponseData updataUsername(@RequestParam("username")String username, HttpServletRequest request)
    {

        try{
            Users name=usersService.findUserByUsername(username);
            if(name==null)
            {
                String jwt=request.getHeader("token");
                Users users=usersService.findUserById(Long.parseLong(tokenService.reUserIdBytoken(jwt)));
                users.setUsername(username);
                int id=usersService.findModifyItself(users);
                ResponseData responseData= ResponseData.createBySuccess();
                responseData.setMsg("修改成功");
                return responseData;
            }
            else
            {
                ResponseData responseData=ResponseData.createByError();
                responseData.setMsg("用户名重复");
                return responseData;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ResponseData responseData=ResponseData.createByError();
            responseData.setMsg("修改失败，系统错误!");
            return responseData;
        }
    }


    @GetMapping("/getPersonalMsg")
    public ResponseData getUserMsg(HttpServletRequest request)
    {
        String jwt=request.getHeader("token");
        Users users=usersService.findUserById(Long.parseLong(tokenService.reUserIdBytoken(jwt)));
        users.setPassword("");
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(users);
        return responseData;
    }




}

