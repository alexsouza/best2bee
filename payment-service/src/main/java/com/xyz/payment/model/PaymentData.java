package com.xyz.payment.model;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;

public class PaymentData {

    private Long externalClientId;
    private Long id;

    @CreditCardNumber(ignoreNonDigitCharacters = true, message = "A valid credit card number is required")
    @NotNull(message = "A credit card number is required")
    private String creditCardNumber;

    @DecimalMin(value = "0.0")
    private BigDecimal amount;

    @NotNull(message = "Currency is required")
    private String currency;

    private Boolean acceped;
    private String status;

    public String getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExternalClientId() {
        return externalClientId;
    }

    public void setExternalClientId(Long externalClientId) {
        this.externalClientId = externalClientId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getAcceped() {
        return acceped;
    }

    public void setAcceped(Boolean acceped) {
        this.acceped = acceped;
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

    @Override
    public String toString() {
        return "PaymentData [acceped=" + acceped + ", amount=" + amount + ", creditCardNumber=" + creditCardNumber
                + ", currency=" + currency + ", externalClientId=" + externalClientId + ", id=" + id + ", status="
                + status + "]";
    }

}