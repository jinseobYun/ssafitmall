package com.ssafy.ssafitmall.model.service;

import com.ssafy.ssafitmall.model.dao.PaymentDao;
import com.ssafy.ssafitmall.model.dto.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentDao paymentDao;

    PaymentServiceImpl(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }


    @Override
    public int writePayment(Payment payment) {
        return  paymentDao.createPayment(payment);
    }

    @Override
    public Payment findPayment(int paymentId) {
        return paymentDao.selectPaymentById(paymentId);
    }

    @Override
    public int removePayment(int paymentId) {
        return paymentDao.deletePaymentById(paymentId);
    }

    @Override
    public int removePaymentByProductCode(int productCode) {
        return paymentDao.deletePaymentByProductCode(productCode);
    }

    @Override
    public Payment findPaymentByProductCode(int productCode) {
        return paymentDao.selectPaymentByProductCode(productCode);
    }
}
