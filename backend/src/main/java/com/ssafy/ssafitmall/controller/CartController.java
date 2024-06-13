package com.ssafy.ssafitmall.controller;

import com.ssafy.ssafitmall.model.dto.Cart;
import com.ssafy.ssafitmall.model.dto.CartInfo;
import com.ssafy.ssafitmall.model.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(value = "*")
public class CartController {

    private CartService cartService;

    private static final String SUCCESS = "success";
    private static final String FAIL_GET_CART = "관심상품 정보 조회 실패";
    private static final String FAIL_GET_LIST = "관심 상품 리스트 조회 실패";
    private static final String FAIL_POST_CART = "관심상품 등록 실패";
    private static final String FAIL_DELETE_CART = "관심상품 삭제 실패";

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCart(@PathVariable("userId") String userId) {
        System.out.println(userId);
        List<Cart> list = cartService.findCartList(userId);

        System.out.println(list);
        if (list == null) {
            return new ResponseEntity<>(FAIL_GET_LIST, HttpStatus.NOT_FOUND);
        }
        List<CartInfo> cartInfoList = new ArrayList<>();
        for (Cart cart : list) {
            CartInfo cartInfo = cartService.findCartInfo(userId, cart.getProductCode());
            if (cartInfo == null) {
                return new ResponseEntity<>(FAIL_GET_CART, HttpStatus.NOT_FOUND);
            }
            cartInfoList.add(cartInfo);
        }
        return new ResponseEntity<>(cartInfoList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addCart(@RequestBody Cart cart) {
        System.out.println(cart);
        int result = cartService.writeCart(cart);
        if (result == 1) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL_POST_CART, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteCart(@RequestParam("cartId") int cartId, @RequestParam("userId") String userId, @RequestParam("productCode") int productCode) {
        if(cartId != -1){
        System.out.println("cart : " + cartId + " " + productCode);
        int result = cartService.removeCart(cartId, productCode);
        if (result == 1) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL_DELETE_CART, HttpStatus.BAD_REQUEST);

        }
        else{
            int result = cartService.removeCartByUserId(userId, productCode);
            if (result == 1) {
                System.out.println("여기로옵니까?");

                return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
            }
            return new ResponseEntity<>(FAIL_POST_CART, HttpStatus.BAD_REQUEST);
        }
    }

}
