package com.ssafy.ssafitmall.model.dto;


import java.sql.Date;

public class Order {
    private int orderNumber;
    private String receiverName;
    private String receiverPhone;
    private String recipientAddress;
    private Date createAt;
    private Date deliveredAt;
    private String userId;
    private String deliveryStatus;
    private int productCode;
    private boolean isDelivered;
    public Order() {
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public Order(String receiverName, String receiverPhone, String recipientAddress, String userId, int productCode) {
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.recipientAddress = recipientAddress;
        this.userId = userId;
        this.productCode = productCode;
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

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", recipientAddress='" + recipientAddress + '\'' +
                ", createAt=" + createAt +
                ", deliveredAt=" + deliveredAt +
                ", userId='" + userId + '\'' +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                '}';
    }
}
