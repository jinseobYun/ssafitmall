package com.ssafy.ssafitmall.model.dao;

import com.ssafy.ssafitmall.model.dto.Order;
import com.ssafy.ssafitmall.model.dto.OrderInfo;
import com.ssafy.ssafitmall.model.dto.OrderState;

import java.util.HashMap;
import java.util.List;

public interface OrderDao {

    public List<Order> selectAllOrders();

    public List<Order> searchByUserId(String userId);

    public Order selectOrderById(int orderNumber);

    public int createOrder(Order order);
    public int updateOrder(Order order);
    public int deleteOrder(int orderNumber);

    public int updateDeleveryStatus(HashMap<String,Object> map);

    // 배달 상태 변경
    int updateDelevery(HashMap<String,Object> map);
    // 주문 상태 만들기
}
