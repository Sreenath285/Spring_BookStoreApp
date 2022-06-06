package com.sreenath.bookstore.controller;

import com.sreenath.bookstore.dto.OrderDTO;
import com.sreenath.bookstore.dto.ResponseDTO;
import com.sreenath.bookstore.model.EmailData;
import com.sreenath.bookstore.model.OrderData;
import com.sreenath.bookstore.service.emailservice.IEmailService;
import com.sreenath.bookstore.service.orderservice.IOrderService;
import com.sreenath.bookstore.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderRestController {
    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IEmailService iEmailService;

    @Autowired
    private TokenUtil tokenUtil;

    /***
     * Implemented getAllOrders method to get all the orders from database
     * @return
     */
    @GetMapping(value = {"", "/"})
    public ResponseEntity<ResponseDTO> getAllOrders() {
        List<OrderData> orderDataList = iOrderService.getAllOrders();
        ResponseDTO responseDTO = new ResponseDTO("Getting all orders", orderDataList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    /***
     * Implemented getOrderById method to get order by id from database
     * @param token - passing token as param
     * @return
     */
    @GetMapping("/get_by_id/{token}")
    public ResponseEntity<ResponseDTO> getOrderById(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        OrderData orderData = iOrderService.getOrderById(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Get order for id", orderData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    /***
     * Implemented verifyOrder method to check the order list
     * @param token - passing token as param
     * @return
     */
    @GetMapping("/verify/{token}")
    public ResponseEntity<ResponseDTO> verifyOrder(@PathVariable("token") String token) {
        OrderData orderData = iOrderService.verifyOrder(token);
        ResponseDTO responseDTO = new ResponseDTO("Your order", orderData, token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    /***
     * Implemented placeOrder method to place the order
     * After placing the order a mail will send to the registered mail id
     * @param orderDTO - passing orderDTO as param
     * @return
     */
    @PostMapping("/place_order")
    public ResponseEntity<ResponseDTO> placeOrder(@RequestBody OrderDTO orderDTO) {
        OrderData orderData = iOrderService.placeOrder(orderDTO);
        String token = tokenUtil.createToken(orderData.getOrderId());
        EmailData emailData = new EmailData(orderData.getCartId().getUserId().getEmail(),
                                     "Order confirmed",
                                      "Hi " + orderData.getCartId().getUserId().getFirstName() +
                                            " " + orderData.getCartId().getUserId().getLastName() +
                                            ", Click on the given below link to get details \n" + iEmailService.getOrderLink(token));
        iEmailService.sendEmail(emailData);
        ResponseDTO responseDTO = new ResponseDTO("Order placed successfully", orderData, token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    /***
     * Implemented cancelOrder method to cancel the order
     * After cancelling the order a mail will send to the registered mail id
     * @param token
     * @return
     */
    @PutMapping("/cancel_order/{token}")
    public ResponseEntity<ResponseDTO> cancelOrder(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        OrderData orderData = iOrderService.cancelOrder(tokenId);
        EmailData emailData = new EmailData(orderData.getCartId().getUserId().getEmail(),
                                     "Cancelled order",
                                      "Hi " + orderData.getCartId().getUserId().getFirstName() +
                                            " " + orderData.getCartId().getUserId().getLastName() +
                                            ", Your order has been cancelled successfully");
        iEmailService.sendEmail(emailData);
        ResponseDTO responseDTO = new ResponseDTO("Order cancelled successfully", "Order id " + tokenId);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
