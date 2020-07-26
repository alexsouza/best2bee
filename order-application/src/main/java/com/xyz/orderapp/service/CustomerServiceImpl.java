package com.xyz.orderapp.service;

import javax.validation.constraints.Min;

import com.xyz.orderapp.exception.ResourceNotFoundException;
import com.xyz.orderapp.model.Customer;
import com.xyz.orderapp.repository.CustomerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(@Min(value = 1, message = "Invalid customer ID.") long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

}