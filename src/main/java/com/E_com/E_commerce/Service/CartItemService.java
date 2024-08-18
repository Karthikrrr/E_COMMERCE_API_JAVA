package com.E_com.E_commerce.Service;


import com.E_com.E_commerce.Exception.CartItemException;
import com.E_com.E_commerce.Exception.UserException;
import com.E_com.E_commerce.model.Cart;
import com.E_com.E_commerce.model.CartItem;
import com.E_com.E_commerce.model.Product;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem)throws UserException, CartItemException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

    public  CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
