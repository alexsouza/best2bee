package com.xyz.orderapp.repository;

import com.xyz.orderapp.model.OrderItem;
import com.xyz.orderapp.model.OrderItemPK;

import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, OrderItemPK> {

}