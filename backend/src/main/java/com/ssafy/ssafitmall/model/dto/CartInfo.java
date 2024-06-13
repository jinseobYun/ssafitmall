package com.ssafy.ssafitmall.model.dto;

import java.sql.Date;

public class CartInfo
{
    private int cartId;
    private String userId;
    private int productCode;
    private String productThumbnail;
    private String productName;
    private int productPrice;

    public CartInfo(int cartId, String userId, int productCode, String productThumbnail, String productName, int productPrice) {
        this.cartId = cartId;
        this.userId = userId;
        this.productCode = productCode;
        this.productThumbnail = productThumbnail;
        this.productName = productName;
        this.productPrice = productPrice;
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

    public String getProductThumbnail() {
        return productThumbnail;
    }

    public void setProductThumbnail(String productThumbnail) {
        this.productThumbnail = productThumbnail;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "CartInfo{" +
                "cartId=" + cartId +
                ", userId='" + userId + '\'' +
                ", productCode=" + productCode +
                ", productThumbnail='" + productThumbnail + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                '}';
    }
}
