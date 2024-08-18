package com.E_com.E_commerce.ServiceImplementation;

import com.E_com.E_commerce.Exception.ProductException;
import com.E_com.E_commerce.Repository.CartRepository;
import com.E_com.E_commerce.Request.AddItemRequest;
import com.E_com.E_commerce.Service.CartItemService;
import com.E_com.E_commerce.Service.CartService;
import com.E_com.E_commerce.Service.ProductService;
import com.E_com.E_commerce.model.Cart;
import com.E_com.E_commerce.model.CartItem;
import com.E_com.E_commerce.model.Product;
import com.E_com.E_commerce.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImplementation implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    @Override
    public Cart createCart(Users users) {
        Cart cart = new Cart();
        cart.setUser(users);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest request) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(request.getProductId());

        CartItem isPresent = cartItemService.isCartItemExist(cart, product, request.getSize(), userId);
        if(isPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setUserId(userId);

            int price = request.getQuantity()*product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(request.getSize());


            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }
        return "Item Add To Cart";
    }

    @Override
    public Cart findUserCart(Long UserId) {
        Cart cart = cartRepository.findByUserId(UserId);

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for(CartItem cartItem : cart.getCartItems()){
            totalPrice= totalPrice + cartItem.getPrice();
            totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
            totalItem = totalItem + cartItem.getQuantity();
        }

        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setDiscount(totalPrice-totalDiscountedPrice);

        return cartRepository.save(cart);
    }
}
