package com.zsc.flower.controller;

import com.zsc.flower.domain.entity.Cart;
import com.zsc.flower.domain.entity.Product;
import com.zsc.flower.domain.vo.ResponseData;
import com.zsc.flower.service.CartService;
import com.zsc.flower.service.ProductImageService;
import com.zsc.flower.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;

    //用户加入购物车
    @RequestMapping(value = "/addCart",method = RequestMethod.POST)
    public ResponseData addCart(@RequestParam("uid")long uid,
                                @RequestParam("pid")long pid,
                                @RequestParam("count")int count){
//      用pid去购物车里头找，看看购物车里有没有这个商品，如果有就在原有的基础上改变数量
        int n=0;
        Cart oldCart=cartService.findCartByUidAndPid(uid,pid);
        if(oldCart!=null){
            long oldCount=oldCart.getCount();
            long newCount=oldCount+count;
            oldCart.setCount((int) newCount);
            cartService.findUpdateCart(oldCart);
            n=1;
        }
        else{
            //根据商品ID找到商品的图片路径，商品名称，商品单价
            Product product=productService.findProductById(pid);
            List<String> productImageList=productImageService.findProductImageUrlById(pid);
            Cart cart=new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setFileurlpath(productImageList.get(0));
            cart.setPname(product.getName());
            cart.setSimplePrice(product.getPromotePrice());
            cart.setCount(count);
            cart.setTotalPrice(count*product.getPromotePrice());
            n=cartService.findAddCart(cart);
        }
        if(n==1)
            return ResponseData.createBySuccess();
        else
            return ResponseData.createByError();
    }
    //展示用户的购物车列表
    @RequestMapping(value = "/listCart",method = RequestMethod.GET)
    public ResponseData listCart(long uid){
        List<Cart> cartList=cartService.findListCartByUid(uid);
        ResponseData responseData=ResponseData.createBySuccess();
        responseData.setData(cartList);
        return responseData;
    }
    //更新购物车的信息
    @RequestMapping(value = "/updateCart",method = RequestMethod.GET)
    public ResponseData updateCart(@RequestParam("id")long id,
                                   @RequestParam("count")int count){
        Cart cart=cartService.findCartById(id);
        long stock=productService.findOldStock(cart.getPid());
        int flag=0;
        if(stock>=count){//库存足够就可以更新购物车
            flag=1;
            cart.setCount(count);
            cart.setTotalPrice(count*cart.getSimplePrice());
            cartService.findUpdateCart(cart);
        }
        if(flag==1)
            return ResponseData.createBySuccess();
        else
            return ResponseData.createByError();
    }
    //删除购物车
    @RequestMapping(value = "/deleteCart",method = RequestMethod.GET)
    public ResponseData deleteCart(@RequestParam("id")long id){
        if(cartService.findDeleteCartById(id)==1)
            return ResponseData.createBySuccess();
        else
            return ResponseData.createByError();
    }
}
