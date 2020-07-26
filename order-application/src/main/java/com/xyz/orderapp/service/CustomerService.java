package com.xyz.orderapp.service;

import javax.validation.constraints.Min;

import com.xyz.orderapp.model.Customer;

import org.springframework.validation.annotation.Validated;

@Validated
public interface CustomerService {

    Iterable<Customer> getAllCustomers();

    Customer getCustomer(@Min(value = 1L, message = "Invalid customer ID.") long id);
}