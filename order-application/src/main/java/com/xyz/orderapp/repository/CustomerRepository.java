package com.xyz.orderapp.repository;

import com.xyz.orderapp.model.Customer;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}