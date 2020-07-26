package com.xyz.orderapp.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.validator.constraints.CreditCardNumber;

@Entity
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreditCardNumber(ignoreNonDigitCharacters = true, message = "A valid credit card number is required")
    @NotNull(message = "A credit card number is required")
    private String creditCardNumber;

    @DecimalMin(value = "0.0")
    private BigDecimal amount;

    @NotNull(message = "Currency is required")
    private String currency;
    private String status;

    @NotNull(message = "Street name is required")
    private String street;

    @NotNull(message = "Street number is required")
    private Integer number;

    @NotNull(message = "City info is required")
    private String city;

    @Transient
    private Long externalClientId;

    @Transient
    private Boolean acceped;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getExternalClientId() {
        return externalClientId;
    }

    public void setExternalClientId(Long externalClientId) {
        this.externalClientId = externalClientId;
    }

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

    public Boolean getAcceped() {
        return acceped;
    }

    public void setAcceped(Boolean acceped) {
        this.acceped = acceped;
    }

    @Override
    public String toString() {
        return "PaymentDetails [acceped=" + acceped + ", amount=" + amount + ", city=" + city + ", creditCardNumber="
                + creditCardNumber + ", currency=" + currency + ", externalClientId=" + externalClientId + ", id=" + id
                + ", number=" + number + ", status=" + status + ", street=" + street + "]";
    }

}