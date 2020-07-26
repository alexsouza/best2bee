package com.xyz.payment.service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.xyz.payment.model.PaymentData;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

    @Override
    @Async
    public CompletableFuture<Boolean> processPayment(
            @NotNull(message = "The transaction cannot be null.") @Valid PaymentData transaction) {
        try {
            Thread.sleep(3000);
            Random random = new Random();
            return CompletableFuture.completedFuture(random.nextBoolean());
        } catch (InterruptedException e) {
            return CompletableFuture.completedFuture(Boolean.FALSE);
        }
    }

}