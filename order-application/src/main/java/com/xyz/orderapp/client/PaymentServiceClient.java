package com.xyz.orderapp.client;

import com.xyz.orderapp.model.PaymentDetails;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", path = "/api/payment")
public interface PaymentServiceClient {

    @PostMapping
    PaymentDetails pay(@RequestBody PaymentDetails transaction);

}