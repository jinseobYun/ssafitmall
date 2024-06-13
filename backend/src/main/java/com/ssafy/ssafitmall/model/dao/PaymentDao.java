package com.ssafy.ssafitmall.model.dao;

import com.ssafy.ssafitmall.model.dto.Payment;

public interface PaymentDao {

    public int createPayment(Payment payment);
    public Payment selectPaymentById(int paymentId);
    public int deletePaymentById(int paymentId);
    public int deletePaymentByProductCode(int productCode);
    public Payment selectPaymentByProductCode(int productCode);
}
