package com.xyz.orderapp.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.xyz.orderapp.model.OrderItem;
import com.xyz.orderapp.repository.OrderItemRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem create(@NotNull(message = "The item for order cannot be null.") @Valid OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public Iterable<OrderItem> createAll(
            @NotNull(message = "The items for order cannot be null.") @Valid Iterable<OrderItem> orderItems) {
        return orderItemRepository.saveAll(orderItems);
    }

}