package com.ssafy.ssafitmall.model.service;

import com.ssafy.ssafitmall.model.dto.Cart;
import com.ssafy.ssafitmall.model.dto.CartInfo;

import java.util.List;

public interface CartService {

    // 관심 상품 추가
    public int writeCart(Cart cart);

    // 관심 상품 리스트 조회
    public List<Cart> findCartList(String userId);

    // 관심 상품 삭제
    public int removeCart(int cartId, int productCode);

    //
    public int removeCartByUserId(String userId, int productCode);

    public CartInfo findCartInfo(String userId, int productCode);
}
