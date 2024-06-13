package com.ssafy.ssafitmall.model.dto;

public class ProductInspection {
    private int productCode;
    private String inspection;

    ProductInspection(int productCode, String inspection) {
        this.productCode = productCode;
        this.inspection = inspection;

    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getInspection() {
        return inspection;
    }

    public void setInspection(String inspection) {
        this.inspection = inspection;
    }

    @Override
    public String toString() {
        return "ProductInspection{" +
                "productCode=" + productCode +
                ", inspection='" + inspection + '\'' +
                '}';
    }
}
