package com.ssafy.ssafitmall.controller;

import com.ssafy.ssafitmall.model.dto.Payment;
import com.ssafy.ssafitmall.model.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@CrossOrigin(value = "*")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    private static final String SUCCESS = "success";
    private static final String FAIL_GET_PAYMENT = "선택 주문 조회 실패";
    private static final String FAIL_POST_PAYMENT = "주문 등록 실패";
    private static final String FAIL_DELETE_PAYMENT = "주문 삭제 실패";


    @GetMapping("/{productCode}")
    public ResponseEntity<?> getPaymentByProductCode(@PathVariable("productCode") int productCode) {
        System.out.println(productCode);
        Payment result = paymentService.findPaymentByProductCode(productCode);
        System.out.println(result);
        if (result == null) {
            return new ResponseEntity<>("FAIL_GET_PAYMENT", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> postPayment(@RequestBody Payment payment) {
        System.out.println(payment);
        int result = paymentService.writePayment(payment);
        if (result == 1) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL_POST_PAYMENT, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{productCode}")
    public ResponseEntity<?> deletePaymentByProductCode(@PathVariable("productCode") int productCode) {
        int result = paymentService.removePaymentByProductCode(productCode);
        if (result == 1) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL_DELETE_PAYMENT, HttpStatus.BAD_REQUEST);
    }



}
