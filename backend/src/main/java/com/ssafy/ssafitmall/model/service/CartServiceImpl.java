package com.ssafy.ssafitmall.model.service;

import com.ssafy.ssafitmall.model.dao.CartDao;
import com.ssafy.ssafitmall.model.dto.Cart;
import com.ssafy.ssafitmall.model.dto.CartInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private CartDao cartDao;

    CartServiceImpl(){

    }
    @Autowired
    CartServiceImpl(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public int writeCart(Cart cart) {
        cartDao.increaseLikeCnt(cart.getProductCode());
        return cartDao.createCart(cart);
    }

    @Override
    public List<Cart> findCartList(String userId) {
        return cartDao.selectCart(userId);
    }

    @Override
    public int removeCart(int cartId,int productCode) {

        cartDao.decreaseLikeCnt(productCode);
        return cartDao.deleteCart(cartId);
    }

    @Override
    public int removeCartByUserId(String userId, int productCode) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("productCode",productCode);
        return cartDao.deleteCartKeyUserId(map);
    }

    @Override
    public CartInfo findCartInfo(String userId, int productCode){
        HashMap<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("productCode",productCode);
        return cartDao.selectCartInfo(map);
    }

}
