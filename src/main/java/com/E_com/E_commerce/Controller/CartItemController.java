package com.E_com.E_commerce.Controller;

import com.E_com.E_commerce.Exception.CartItemException;
import com.E_com.E_commerce.Exception.UserException;
import com.E_com.E_commerce.Responce.ApiResponse;
import com.E_com.E_commerce.Service.CartItemService;
import com.E_com.E_commerce.Service.UserService;
import com.E_com.E_commerce.model.CartItem;
import com.E_com.E_commerce.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws UserException, CartItemException{
        Users user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse response = new ApiResponse();
        response.setMessage("Item Removed From Cart");
        response.setStatus(true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem, @PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws UserException, CartItemException{
        Users user =  userService.findUserProfileByJwt(jwt);

        CartItem updateItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

        return new ResponseEntity<>(updateItem, HttpStatus.OK);
    }



}
