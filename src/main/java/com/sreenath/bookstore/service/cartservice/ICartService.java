package com.sreenath.bookstore.service.cartservice;

import com.sreenath.bookstore.dto.CartDTO;
import com.sreenath.bookstore.model.CartData;


public interface ICartService {
    CartData addToCart(CartDTO cartDTO);

    Iterable<CartData> findAllCarts();

    CartData getCartById(int cartId);

    CartData updateQuantity(int cartId, int quantity);

    void deleteCart(int cartId);
}
