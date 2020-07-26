package com.xyz.shippingservice.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProductData {

    @NotNull(message = "Product name is required")
    private String name;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductData [name=" + name + ", quantity=" + quantity + "]";
    }

}
