package com.E_com.E_commerce.Controller;


import com.E_com.E_commerce.Exception.ProductException;
import com.E_com.E_commerce.Exception.UserException;
import com.E_com.E_commerce.Request.AddItemRequest;
import com.E_com.E_commerce.Responce.ApiResponse;
import com.E_com.E_commerce.Service.CartService;
import com.E_com.E_commerce.Service.UserService;
import com.E_com.E_commerce.model.Cart;
import com.E_com.E_commerce.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException{
        Users user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest request, @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
        Users user = userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(), request);

        ApiResponse response = new ApiResponse();
        response.setMessage("Item Added To Cart");
        response.setStatus(true);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
