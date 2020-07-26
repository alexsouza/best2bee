package com.xyz.payment.service;

import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.xyz.payment.model.PaymentData;

public interface PaymentGatewayService {

    CompletableFuture<Boolean> processPayment(
            @NotNull(message = "The transaction cannot be null.") @Valid PaymentData data);
}