package com.ssafy.ssafitmall.model.dto;

import org.springframework.stereotype.Component;

@Component
public class ProductCategory {

    private String categoryCode;
    private String categoryName;

    public ProductCategory() {

    }

    public ProductCategory(String categoryCode, String categoryName) {
        super();
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
