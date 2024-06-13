package com.ssafy.ssafitmall.model.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;


@Component
public class Product {
    private int productCode;
    private String productThumbnail;
    private String productName;
    private int productPrice;
    private String productDesc;
    private Date productDate;
    private int productHist;
    private String categoryCode;
    private String userId;
    private String isSold;
    private String inspection;
    private int likeCnt;

    public Product() {

    }

    public Product(String productName, String productThumbnail, int productPrice, String productDesc, String categoryCode, String userId) {
        super();
        this.productName = productName;
        this.productThumbnail = productThumbnail;
        this.productPrice = productPrice;
        this.productDesc = productDesc;
        this.categoryCode = categoryCode;
        this.userId = userId;
    }

    public String getIsSold() {
        return isSold;
    }

    public void setIsSold(String isSold) {
        this.isSold = isSold;
    }

    public String getInspection() {
        return inspection;
    }

    public void setInspection(String inspection) {
        this.inspection = inspection;
    }

    public int getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public int getProductHist() {
        return productHist;
    }

    public void setProductHist(int productHist) {
        this.productHist = productHist;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productCode=" + productCode +
                ", productThumbnail='" + productThumbnail + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDesc='" + productDesc + '\'' +
                ", productDate=" + productDate +
                ", productHist=" + productHist +
                ", categoryCode='" + categoryCode + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
