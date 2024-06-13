package com.ssafy.ssafitmall.model.dao;

import com.ssafy.ssafitmall.model.dto.Cart;
import com.ssafy.ssafitmall.model.dto.CartInfo;

import java.util.HashMap;
import java.util.List;

public interface CartDao {

    // 장바구니에 상품 추가
    public int createCart(Cart cart);

    // 장바구니 상품 조회
    public List<Cart> selectCart(String userId);

    //장바구니 상품 제거
    public int deleteCart(int cartId);

    //장바구니 상품 제거 ( userId + productCode)
    public int deleteCartKeyUserId(HashMap<String,Object> con);

    // 상품의 관심상품 등록 횟수 증가
    public int increaseLikeCnt(int productCode);

    // 상품의 관심상품 등록 횟수 감소
    public int decreaseLikeCnt(int productCode);

    public CartInfo selectCartInfo(HashMap<String,Object> hashmap);
}
