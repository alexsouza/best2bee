package com.xyz.orderapp.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.xyz.orderapp.model.OrderItem;

public interface OrderItemService {

    OrderItem create(@NotNull(message = "The item for order cannot be null.") @Valid OrderItem orderItem);

    Iterable<OrderItem> createAll(
            @NotNull(message = "The items for order cannot be null.") @Valid Iterable<OrderItem> orderItems);
}