package com.ssafy.ssafitmall.model.dto;

public class ProductImg {
    private int imgId;
    private String originalName;
    private String saveName;
    private int productCode;

    public ProductImg() {
    }

    public ProductImg(String originalName, String saveName, int productCode) {
        this.originalName = originalName;
        this.saveName = saveName;
        this.productCode = productCode;
    }

    public int getImgId() {

        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getOriginalName() {

        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getSaveName() {
        return saveName;

    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;

    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return "ProductImg{" +
                "imgId=" + imgId +
                ", originalName='" + originalName + '\'' +
                ", saveName='" + saveName + '\'' +
                ", productCode=" + productCode +
                '}';
    }
}
