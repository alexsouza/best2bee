package com.xyz.shippingservice.service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Override
    @Async
    public CompletableFuture<Boolean> startProcess() {
        try {
            Thread.sleep(5000);
            Random random = new Random();
            return CompletableFuture.completedFuture(random.nextBoolean());
        } catch (InterruptedException e) {
            return CompletableFuture.completedFuture(Boolean.FALSE);
        }
    }

}