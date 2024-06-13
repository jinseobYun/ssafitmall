package com.ssafy.ssafitmall.controller;

import com.ssafy.ssafitmall.model.dto.Order;
import com.ssafy.ssafitmall.model.dto.OrderInfo;
import com.ssafy.ssafitmall.model.dto.OrderState;
import com.ssafy.ssafitmall.model.service.OrderService;
import com.ssafy.ssafitmall.model.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(value = "*")
public class OrderController {

    private static final String SUCCESS = "success";
    private static final String FAIL_GET_LIST = "전체 주문 조회 실패";
    private static final String FAIL_GET_ORDER = "선택 주문 조회 실패";
    private static final String FAIL_POST_ORDER = "주문 등록 실패";
    private static final String FAIL_PUT_ORDER = "주문 수정 실패";
    private static final String FAIL_DELETE_ORDER = "주문 삭제 실패";

    OrderService orderService;
    ProductService productService;

    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    // 전체 주문 조회
    @GetMapping("/")
    public ResponseEntity<?> getAllOrder() {
        List<Order> list = orderService.findAllOrders();
        if (list == null || list.isEmpty()) {
            return new ResponseEntity<>(FAIL_GET_LIST, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 주문 번호로 주문 조회
    @GetMapping("/{orderNumber}")
    public ResponseEntity<?> getOrder(@PathVariable("orderNumber") int orderNumber) {
        Order order = orderService.findOrderById(orderNumber);
        if (order == null) {
            return new ResponseEntity<>(FAIL_GET_ORDER, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // 유저 아이디로 주문 리스트 조회
    @GetMapping("user/{userId}")
    public ResponseEntity<?> getUserOrder(@PathVariable("userId") String userId) {
        List<Order> list = orderService.findOrdersByCustomerId(userId);
        if (list == null || list.isEmpty()) {
            return new ResponseEntity<>(FAIL_GET_LIST, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 주문 등록
    @PostMapping("/{productCode}")
    public ResponseEntity<?> addOrder(@PathVariable("productCode") int productCode, @RequestBody Order order) {
        if (order == null) {
            return new ResponseEntity<>(FAIL_POST_ORDER, HttpStatus.BAD_REQUEST);
        }
        int result = orderService.writeOrder(order);
        if (result == 1) {
            int change = productService.modifyProductSold(productCode,"거래중");
            if (change == 0) {
                return new ResponseEntity<>(FAIL_PUT_ORDER, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(order.getOrderNumber(), HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL_PUT_ORDER, HttpStatus.BAD_REQUEST);
    }

    // 주문 수정
    @PutMapping("/")
    public ResponseEntity<?> updateOrder(@RequestBody Order order) {
        if (order == null) {
            return new ResponseEntity<>(FAIL_PUT_ORDER, HttpStatus.BAD_REQUEST);
        }
        int result = orderService.modifyOrder(order);
        if (result == 1) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL_PUT_ORDER, HttpStatus.BAD_REQUEST);
    }

    // 주문 삭제
    @DeleteMapping("/{orderNumber}")
    public ResponseEntity<?> deleteOrder(@PathVariable("orderNumber") int orderNumber) {


        Order order = orderService.findOrderById(orderNumber);
        if (order == null) {
            return new ResponseEntity<>(FAIL_DELETE_ORDER, HttpStatus.NOT_FOUND);
        }
        int change = productService.modifyProductSold(order.getProductCode(),"판매중");
        if(change == 0){
            return new ResponseEntity<>(FAIL_DELETE_ORDER, HttpStatus.OK);
        }

        int result = orderService.removeOrder(orderNumber);
        if (result == 1) {

            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL_DELETE_ORDER, HttpStatus.BAD_REQUEST);
    }

    //주문 상태 수정
    @PutMapping("status/{orderNumber}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable("orderNumber") int orderNumber,@RequestBody Order order) {

        if (order == null ) {
            return new ResponseEntity<>(FAIL_PUT_ORDER, HttpStatus.BAD_REQUEST);
        }
        int result = orderService.modifyOrderStatus(orderNumber,order.getDeliveryStatus());
        if (result == 1) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL_PUT_ORDER, HttpStatus.BAD_REQUEST);
    }
}
