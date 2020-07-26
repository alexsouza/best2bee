package com.xyz.orderapp.service;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.xyz.orderapp.model.Order;

public interface OrderService {

    Iterable<Order> getAll();

    Order get(@Min(value = 1L, message = "Invalid order ID.") long id);

    Order create(@NotNull(message = "The order cannot be null.") @Valid Order order);

    void update(@NotNull(message = "The order cannot be null.") @Valid Order order);

    void delete(Order order);

}