package com.ssafy.ssafitmall.model.dto;

public class ProductSearchCondition {

    // 상품 카테고리 기본 : 전체
    // - ( 전체, 유기농,프로틴,게이너,아미노산, 다이어트, 에너지, 영양제, 쉐이커, 헬스 용품 )
    private String productCategory = "ALL";
    // 검색 조건 기본 : none
    // - ( 'none' , 인기순, 신상품 순, 리뷰순, 높은 가격순, 낮은 가격순 )
    private String searchType = "none";

    // 검색어 입력
    private String keyword ="";

    private String inspection = "";

    private int limit = 0;

    public ProductSearchCondition() {
    }

    ProductSearchCondition(String productCategory, String searchType, String inspection, String keyword, int limit) {
        this.productCategory = productCategory;
        this.searchType = searchType;
        this.inspection = inspection;
        this.keyword = keyword;
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getInspection() {
        return inspection;
    }

    public void setInspection(String inspection) {
        this.inspection = inspection;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    @Override
    public String toString() {
        return "ProductSearchCondition{" +
                "productCategory='" + productCategory + '\'' +
                ", searchType='" + searchType + '\'' +
                ", inspection='" + inspection + '\'' +
                ", keyword='" + keyword + '\'' +
                ", limit=" + limit +
                '}';
    }
}
