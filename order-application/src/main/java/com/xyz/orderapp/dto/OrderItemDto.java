package com.xyz.orderapp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.xyz.orderapp.model.Product;

public class OrderItemDto {

    @NotNull(message = "Order item must have a product info")
    private Product product;

    @Min(message = "Order quantity must be at least 1", value = 1)
    @NotNull(message = "Order item quantity is required")
    private Integer quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemDto [product=" + product + ", quantity=" + quantity + "]";
    }

}