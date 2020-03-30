package com.zsc.flower.service;

import com.zsc.flower.domain.entity.Cart;
import com.zsc.flower.domain.entity.CartDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    public CartDetail findCartDetail(long id);
    public int findAddCart(Cart cart);
    public List<Cart> findListCartByUid(long uid);
    public Cart findCartByUidAndPid(long uid, long pid);
    public Cart findCartById(long id);
    public void findUpdateCart(Cart cart);
    public int findDeleteCartById(long id);
    public void findDeleteCartByUidAndPid(long uid, long pid);
}
