package com.E_com.E_commerce.ServiceImplementation;

import com.E_com.E_commerce.Repository.OrderItemRepository;
import com.E_com.E_commerce.Service.OrderItemService;
import com.E_com.E_commerce.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImplementation implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
