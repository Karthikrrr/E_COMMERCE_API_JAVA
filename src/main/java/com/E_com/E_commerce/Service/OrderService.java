package com.E_com.E_commerce.Service;

import com.E_com.E_commerce.Exception.OrderException;
import com.E_com.E_commerce.model.Address;
import com.E_com.E_commerce.model.Orders;
import com.E_com.E_commerce.model.Users;

import java.util.List;

public interface OrderService {

    public Orders createOrder(Users user, Address shippingAddress);

    public Orders findOrderById(Long orderId) throws OrderException;

    public List<Orders> userOrderHistory(Long UserId);

    public Orders placedOrder(Long orderId) throws OrderException;

    public Orders confirmedOrder(Long orderId) throws OrderException;

    public Orders shippingOrder(Long orderID) throws OrderException;

    public Orders deliveredOrder(Long orderId) throws OrderException;

    public Orders canceledOrder(Long orderId) throws OrderException;

    public List<Orders> getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;
}
