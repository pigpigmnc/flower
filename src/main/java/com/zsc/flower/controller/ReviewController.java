package com.zsc.flower.controller;

import com.zsc.flower.domain.entity.Review;
import com.zsc.flower.domain.vo.ResponseData;
import com.zsc.flower.service.Impl.ReviewServiceImpl;
import com.zsc.flower.service.Impl.TokenService;
import com.zsc.flower.service.Impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/review")
public class ReviewController {
    @Autowired
    ReviewServiceImpl reviewService;
    @Autowired
    UsersServiceImpl usersService;
    @Autowired
    TokenService tokenService;

    //展示用户的购物车列表
    @RequestMapping(value = "/listReview",method = RequestMethod.GET)
    public ResponseData listCart(@RequestParam("pid") long pid){
        try{
            List<Review> reviewList=reviewService.findReviewByPid(pid);
            ResponseData responseData=ResponseData.createBySuccess();
            responseData.setData(reviewList);
            return responseData;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ResponseData responseData=ResponseData.createByError();
            responseData.setMsg("系统出错");
            return responseData;
        }
    }


}