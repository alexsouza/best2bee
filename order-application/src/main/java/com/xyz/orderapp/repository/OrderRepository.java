package com.xyz.orderapp.repository;

import com.xyz.orderapp.model.Order;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

}