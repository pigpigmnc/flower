package com.zsc.flower.controller;

import com.zsc.flower.domain.entity.Property;
import com.zsc.flower.domain.vo.ResponseData;
import com.zsc.flower.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class testController {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseData test(long id)
    {
        int n;
//        n=productService.findCategoryCount(2L);//这里传入种类id,现在模拟输入种类ID1.表示是短袖
        n=productService.findCategoryCount(id);//这里传入种类id,现在模拟输入种类ID1.表示是短袖
        List<Property> propertylist=new ArrayList<>();
        for(int i=1;i<=n;i++){
//            propertylist=productService.findListProperty(2L);//这里传入种类id,现在模拟输入种类ID1.获取该类下的属性名字列表
            propertylist=productService.findListProperty(id);//这里传入种类id,现在模拟输入种类ID1.获取该类下的属性名字列表
        }
        List<Property> test=new ArrayList<>();
        for(int i=0;i<n;i++)
        {
                test.add(propertylist.get(i));
        }
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(test);
        return responseData;
    }
}
