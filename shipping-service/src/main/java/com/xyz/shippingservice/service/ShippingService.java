package com.xyz.shippingservice.service;

import java.util.concurrent.CompletableFuture;

public interface ShippingService {

    CompletableFuture<Boolean> startProcess();
}