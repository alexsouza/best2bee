package com.xyz.orderapp.model;

import javax.validation.constraints.NotNull;

public class AddressDto {

    @NotNull(message = "Street name is required")
    private String street;

    @NotNull(message = "Street number is required")
    private Integer number;

    @NotNull(message = "City info is required")
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Adress [city=" + city + ", number=" + number + ", street=" + street + "]";
    }
}
