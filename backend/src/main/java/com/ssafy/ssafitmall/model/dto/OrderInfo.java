package com.ssafy.ssafitmall.model.dto;


import java.sql.Date;

public class OrderInfo {
    private int orderNumber;
    private String receiverName;
    private String receiverPhone;
    private String recipientAddress;
    private Date createAt;
    private Date deliveredAt;
    private String userId;
    private String deliveryStatus;
    private int statusId;
    private int productCode;
    private boolean isDelevered;

    OrderInfo(){

    }
    public OrderInfo(String receiverName, String receiverPhone, String recipientAddress, String userId, int productCode, boolean isDelevered) {
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.recipientAddress = recipientAddress;
        this.userId = userId;
        this.productCode = productCode;
        this.isDelevered = isDelevered;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Date deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
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
        return "OrderInfo{" +
                "orderNumber=" + orderNumber +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", recipientAddress='" + recipientAddress + '\'' +
                ", createAt=" + createAt +
                ", deliveredAt=" + deliveredAt +
                ", userId='" + userId + '\'' +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                ", statusId=" + statusId +
                ", productCode=" + productCode +
                ", isDelevered=" + isDelevered +
                '}';
    }
}
