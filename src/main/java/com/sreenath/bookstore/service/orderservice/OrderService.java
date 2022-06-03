package com.sreenath.bookstore.service.orderservice;

import com.sreenath.bookstore.dto.OrderDTO;
import com.sreenath.bookstore.exceptions.BookStoreCustomException;
import com.sreenath.bookstore.model.BookData;
import com.sreenath.bookstore.model.CartData;
import com.sreenath.bookstore.model.OrderData;
import com.sreenath.bookstore.model.UserRegistrationData;
import com.sreenath.bookstore.repository.OrderRepository;
import com.sreenath.bookstore.service.bookservice.IBookService;
import com.sreenath.bookstore.service.cartservice.ICartService;
import com.sreenath.bookstore.service.userregistrationservice.IUserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private IUserRegistrationService iUserRegistrationService;

    @Autowired
    private IBookService iBookService;

    @Override
    public OrderData placeOrder(OrderDTO orderDTO) {
        UserRegistrationData userRegistrationData = iUserRegistrationService.getUserRegistrationDataByUserId(orderDTO.getUserId());
        BookData bookData = iBookService.getBookById(orderDTO.getBookId());
        OrderData orderData = new OrderData(userRegistrationData, bookData, orderDTO);
        return orderRepository.save(orderData);
    }

    @Override
    public List<OrderData> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderData getOrderById(int orderId) {
        return orderRepository.findById(orderId)
                              .orElseThrow(() -> new BookStoreCustomException("Order with id " + orderId + " not found"));
    }

    @Override
    public OrderData cancelOrder(int orderId) {
        OrderData orderData = this.getOrderById(orderId);
        orderData.setCancel(true);
        return orderRepository.save(orderData);
    }
}
