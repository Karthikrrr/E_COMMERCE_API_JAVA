package com.E_com.E_commerce.ServiceImplementation;

import com.E_com.E_commerce.Exception.OrderException;
import com.E_com.E_commerce.Repository.AddressRepository;
import com.E_com.E_commerce.Repository.OrderItemRepository;
import com.E_com.E_commerce.Repository.OrderRepository;
import com.E_com.E_commerce.Repository.UserRepository;
import com.E_com.E_commerce.Service.CartService;
import com.E_com.E_commerce.Service.OrderItemService;
import com.E_com.E_commerce.Service.OrderService;
import com.E_com.E_commerce.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderItemRepository orderItemRepository;




    @Override
    public Orders createOrder(Users user, Address shippingAddress) {
        // Associate the shipping address with the user and save the address
        shippingAddress.setUser(user);
        Address savedAddress = addressRepository.save(shippingAddress);
        user.getAddress().add(savedAddress);
        userRepository.save(user);

        // Retrieve the user's cart
        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        // Create and save order items from cart items
        for (CartItem cartItem : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem();

            orderItem.setPrice(cartItem.getPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSize(cartItem.getSize());
            orderItem.setUserId(cartItem.getUserId());
            orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        // Create and save the order
        Orders createdOrder = new Orders();
        createdOrder.setUser(user);
        createdOrder.setOrderitems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscount(cart.getDiscount());
        createdOrder.setTotalItem(cart.getTotalItem());
        createdOrder.setShippindAdress(savedAddress);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");

        createdOrder.getPaymentDetails().setPaymentStatus("PENDING");
        createdOrder.setCreatedAt(LocalDateTime.now());

        Orders savedOrder = orderRepository.save(createdOrder);

        // Associate each order item with the saved order
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(savedOrder);
            orderItemRepository.save(orderItem);
        }

        return savedOrder;
    }


    @Override
    public Orders findOrderById(Long orderId) throws OrderException {
        Optional<Orders> opt = orderRepository.findById(orderId);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new OrderException("Order Not Exist With Id : " + orderId);
    }

    @Override
    public List<Orders> userOrderHistory(Long UserId) {

        return orderRepository.getUsersOrder(UserId);
    }

    @Override
    public Orders placedOrder(Long orderId) throws OrderException {
        Orders order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setPaymentStatus("COMPLETED");

        return order;
    }

    @Override
    public Orders confirmedOrder(Long orderId) throws OrderException {
        Orders order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Orders shippingOrder(Long orderID) throws OrderException {
        Orders order = findOrderById(orderID);
        order.setOrderStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public Orders deliveredOrder(Long orderId) throws OrderException {
        Orders order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");

        return orderRepository.save(order);
    }

    @Override
    public Orders canceledOrder(Long orderId) throws OrderException {
        Orders order = findOrderById(orderId);
        order.setOrderStatus("CANCELLED");

        return orderRepository.save(order);
    }

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Orders order = findOrderById(orderId);

        orderRepository.deleteById(orderId);

    }
}
