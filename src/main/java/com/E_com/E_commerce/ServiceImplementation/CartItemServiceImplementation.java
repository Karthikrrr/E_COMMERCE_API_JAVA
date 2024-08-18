package com.E_com.E_commerce.ServiceImplementation;

import com.E_com.E_commerce.Exception.CartItemException;
import com.E_com.E_commerce.Exception.UserException;
import com.E_com.E_commerce.Repository.CartItemRepository;
import com.E_com.E_commerce.Repository.CartRepository;
import com.E_com.E_commerce.Service.CartItemService;
import com.E_com.E_commerce.Service.UserService;
import com.E_com.E_commerce.model.Cart;
import com.E_com.E_commerce.model.CartItem;
import com.E_com.E_commerce.model.Product;
import com.E_com.E_commerce.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImplementation implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;



    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws UserException, CartItemException {
        CartItem item = findCartItemById(id);
        Users user = userService.findUserById(item.getUserId());
        if(user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        return cartItemRepository.isCartItemExists(cart,product,size,userId);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = findCartItemById(cartItemId);
        Users users = userService.findUserById(cartItem.getUserId());
        Users requestUser = userService.findUserById(userId);
        if(users.getId().equals(requestUser.getId())){
            cartItemRepository.deleteById(cartItemId);
        }
        else {
           throw new UserException("You Can't Remove Another Users Item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> optional = cartItemRepository.findById(cartItemId);
        if (optional.isPresent()){
            return optional.get();
        }
       throw new CartItemException("CartItem Not Found With Id : " + cartItemId);
    }
}
