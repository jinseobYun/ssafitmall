package com.ssafy.ssafitmall.model.service;

import com.ssafy.ssafitmall.model.dto.Order;
import com.ssafy.ssafitmall.model.dto.OrderInfo;
import com.ssafy.ssafitmall.model.dto.OrderState;

import java.util.List;

public interface OrderService {
    // 전체 주문 정보 조회
    public List<Order> findAllOrders();
    // 주문 정보 한개 조회
    public Order findOrderById(int id);
    // 유저 주문 정보 조회
    public List<Order> findOrdersByCustomerId(String customerId);
    public int writeOrder(Order order);
    public int modifyOrder(Order order);
    public int removeOrder(int orderNumber);
    public int modifyOrderStatus(int orderNumber, String status);

    int modifyDelivery(int orderNumber, boolean status);

}
