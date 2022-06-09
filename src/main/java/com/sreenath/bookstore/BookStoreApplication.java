package com.sreenath.bookstore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BookStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
        log.info("Welcome to Book Store Application");
    }
}
