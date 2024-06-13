package com.ssafy.ssafitmall.model.service;

import com.ssafy.ssafitmall.model.dao.OrderDao;
import com.ssafy.ssafitmall.model.dto.Order;
import com.ssafy.ssafitmall.model.dto.OrderInfo;
import com.ssafy.ssafitmall.model.dto.OrderState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    OrderDao orderDao;

    OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> findAllOrders() {
        return orderDao.selectAllOrders();
    }

    @Override
    public Order findOrderById(int orderNumber) {
        return orderDao.selectOrderById(orderNumber);
    }

    @Override
    public List<Order> findOrdersByCustomerId(String customerId) {
        return orderDao.searchByUserId(customerId);
    }

    @Override
    public int writeOrder(Order order) {
        return orderDao.createOrder(order);
    }

    @Override
    public int modifyOrder(Order order) {
        return orderDao.updateOrder(order);
    }

    @Override
    public int removeOrder(int orderNumber) {
        return orderDao.deleteOrder(orderNumber);
    }

    @Override
    public int modifyOrderStatus(int orderNumber, String status) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderNumber", orderNumber);
        map.put("status", status);
        return orderDao.updateDeleveryStatus(map);
    }


    @Override
    public int modifyDelivery(int orderNumber, boolean status) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderNumber", orderNumber);
        map.put("status", status);
        return orderDao.updateDelevery(map);
    }

}
