package com.zsc.flower.dao;

import com.zsc.flower.domain.entity.Cart;
import com.zsc.flower.domain.entity.CartDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao extends BaseMapper<Cart> {
    public CartDetail selectCartDetail(long id);
    public int selectAddCart(Cart cart);
    public List<Cart> selectListCartByUid(@Param("uid") long uid);
    public Cart selectCartByUidAndPid(@Param("uid") long uid, @Param("pid") long pid);
    public Cart selectCartById(@Param("id") long id);
    public void selectUpdateCart(Cart cart);
    public int selectDeleteCartById(@Param("id") long id);
    public void selectDeleteCartByUidAndPid(@Param("uid") long uid, @Param("pid") long pid);
}
