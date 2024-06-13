package com.ssafy.ssafitmall.model.dto;

public class OrderState {

    private int statusId;
    private int orderNumber;
    private int productCode;
    private boolean isDelevered;

    public OrderState(){

    }

    public OrderState(int orderNumber, int productCode) {
        this.orderNumber = orderNumber;
        this.productCode = productCode;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public boolean isDelevered() {
        return isDelevered;
    }

    public void setDelevered(boolean delevered) {
        isDelevered = delevered;
    }

    @Override
    public String toString() {
        return "OrderState{" +
                "statusId=" + statusId +
                ", orderNumber=" + orderNumber +
                ", productCode=" + productCode +
                ", isDelevered=" + isDelevered +
                '}';
    }
}
