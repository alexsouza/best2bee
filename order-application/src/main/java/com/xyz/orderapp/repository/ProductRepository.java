package com.xyz.orderapp.repository;

import com.xyz.orderapp.model.Product;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

}