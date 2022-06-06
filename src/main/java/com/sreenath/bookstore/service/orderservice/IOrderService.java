package com.sreenath.bookstore.service.orderservice;

import com.sreenath.bookstore.dto.OrderDTO;
import com.sreenath.bookstore.model.BookData;
import com.sreenath.bookstore.model.OrderData;

import java.util.List;

public interface IOrderService {
    OrderData placeOrder(OrderDTO orderDTO);

    List<OrderData> getAllOrders();

    OrderData getOrderById(int orderId);

    OrderData cancelOrder(int orderId);

    OrderData verifyOrder(String token);
}
