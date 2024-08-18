package com.E_com.E_commerce.Controller;

import com.E_com.E_commerce.Exception.OrderException;
import com.E_com.E_commerce.Exception.UserException;
import com.E_com.E_commerce.Service.OrderService;
import com.E_com.E_commerce.Service.UserService;
import com.E_com.E_commerce.model.Address;
import com.E_com.E_commerce.model.Orders;
import com.E_com.E_commerce.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Orders> createOrder(@RequestBody Address shippingAddress, @RequestHeader("Authorization") String jwt) throws UserException{

        Users user = userService.findUserProfileByJwt(jwt);
        Orders order = orderService.createOrder(user, shippingAddress);
        return new ResponseEntity<Orders>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Orders>> userOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException{
        Users user = userService.findUserProfileByJwt(jwt);
        List<Orders> orderHistory = orderService.userOrderHistory(user.getId());

        return new ResponseEntity<>(orderHistory, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> findOrderById(@PathVariable("id") Long orderId, @RequestHeader("Authorization") String jwt) throws UserException, OrderException{
        Users user = userService.findUserProfileByJwt(jwt);
        Orders order = orderService.findOrderById(orderId);

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }
}
