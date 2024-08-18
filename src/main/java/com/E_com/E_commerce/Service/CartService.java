package com.E_com.E_commerce.Service;


import com.E_com.E_commerce.Exception.ProductException;
import com.E_com.E_commerce.Request.AddItemRequest;
import com.E_com.E_commerce.model.Cart;
import com.E_com.E_commerce.model.Users;

public interface CartService {

    public Cart createCart(Users users);

    public String addCartItem(Long userId, AddItemRequest request) throws ProductException;

    public Cart findUserCart(Long UserId);
}
