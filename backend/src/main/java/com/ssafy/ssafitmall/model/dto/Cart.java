package com.ssafy.ssafitmall.model.dto;

public class Cart {
    private int cartId;
    private String userId;
    private int productCode;

    Cart(){}

    public Cart(String userId, int productCode) {
        this.userId = userId;
        this.productCode = productCode;
    }

    public Cart(int cartId, String userId, int productCode) {
        this.cartId = cartId;
        this.userId = userId;
        this.productCode = productCode;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                ", productCode=" + productCode +
                '}';
    }
}
