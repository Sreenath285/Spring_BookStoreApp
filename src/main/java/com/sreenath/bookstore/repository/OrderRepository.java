package com.sreenath.bookstore.repository;

import com.sreenath.bookstore.model.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderData, Integer> {
}
