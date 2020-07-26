package com.xyz.orderapp.service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.xyz.orderapp.exception.ResourceNotFoundException;
import com.xyz.orderapp.model.Product;
import com.xyz.orderapp.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public @NotNull Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(@Min(value = 1, message = "Invalid product ID.") long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public Product save(@NotNull Product product) {
        return productRepository.save(product);
    }

}