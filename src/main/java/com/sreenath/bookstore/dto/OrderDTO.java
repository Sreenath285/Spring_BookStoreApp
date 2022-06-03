package com.sreenath.bookstore.dto;

import lombok.Data;

import java.time.LocalDate;

public @Data class OrderDTO {
    public int userId;
    public int bookId;
    public String address;
    public LocalDate orderDate = LocalDate.now();
    public int totalPrice;
    public boolean cancel;
}
