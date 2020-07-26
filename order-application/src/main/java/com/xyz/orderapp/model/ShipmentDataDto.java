package com.xyz.orderapp.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ShipmentDataDto {

    private String trackId;

    private String externalClientId;

    @NotEmpty(message = "Must be provided at least one product")
    @Valid
    List<ProductDataDto> products;

    @NotNull(message = "Adress info is required")
    @Valid
    AddressDto address;

    private String status;

    public String getTrackId() {
        return trackId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getExternalClientId() {
        return externalClientId;
    }

    public void setExternalClientId(String externalClientId) {
        this.externalClientId = externalClientId;
    }

    public List<ProductDataDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDataDto> products) {
        this.products = products;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ShipmentData [address=" + address + ", externalClientId=" + externalClientId + ", products=" + products
                + ", status=" + status + ", trackId=" + trackId + "]";
    }

}