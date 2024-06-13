package com.ssafy.ssafitmall.model.service;

import com.ssafy.ssafitmall.model.dto.Payment;

public interface PaymentService {

    public int writePayment(Payment payment);

    public Payment findPayment(int paymentId);

    public int removePayment(int paymentId);

    public int removePaymentByProductCode(int productCode);

    public Payment findPaymentByProductCode(int productCode);
}
