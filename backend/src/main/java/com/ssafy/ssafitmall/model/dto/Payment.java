package com.ssafy.ssafitmall.model.dto;

public class Payment {

    int paymentId;
    int partnerOrderId;
    String partnerUserId;
    String itemName;
    int quantity;
    int totalAmount;
    int vatAmount;
    int taxFreeAmount;
    int productCode;
    String tid;

    public Payment(int partnerOrderId, String partnerUserId, String itemName, int quantity, int totalAmount, int vatAmount, int taxFreeAmount, int productCode, String tid) {
        this.partnerOrderId = partnerOrderId;
        this.partnerUserId = partnerUserId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.vatAmount = vatAmount;
        this.taxFreeAmount = taxFreeAmount;
        this.productCode = productCode;
        this.tid = tid;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getPartnerOrderId() {
        return partnerOrderId;
    }

    public void setPartnerOrderId(int partnerOrderId) {
        this.partnerOrderId = partnerOrderId;
    }

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(String partnerUserId) {
        this.partnerUserId = partnerUserId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(int vatAmount) {
        this.vatAmount = vatAmount;
    }

    public int getTaxFreeAmount() {
        return taxFreeAmount;
    }

    public void setTaxFreeAmount(int taxFreeAmount) {
        this.taxFreeAmount = taxFreeAmount;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", partnerOrderId=" + partnerOrderId +
                ", partnerUserId='" + partnerUserId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                ", vatAmount=" + vatAmount +
                ", taxFreeAmount=" + taxFreeAmount +
                ", productCode=" + productCode +
                ", tid='" + tid + '\'' +
                '}';
    }
}
