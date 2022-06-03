package com.sreenath.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sreenath.bookstore.dto.OrderDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Table(name = "order_table")
public @Data class OrderData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private int orderId;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserRegistrationData userId;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookData bookId;

    @Column(name = "address")
    private String address;

    @Column(name = "order_date")
    private LocalDate orderDate = LocalDate.now();

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "cancel")
    private boolean cancel;

    public OrderData(UserRegistrationData userId, BookData bookId, OrderDTO orderDTO) {
        this.userId = userId;
        this.bookId = bookId;
        orderData(orderDTO);
    }

    public void orderData(OrderDTO orderDTO) {
        this.address = orderDTO.address;
        this.orderDate = orderDTO.orderDate;
        this.totalPrice = orderDTO.totalPrice;
        this.cancel = orderDTO.cancel;
    }
}
