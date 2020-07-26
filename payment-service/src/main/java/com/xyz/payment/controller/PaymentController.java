package com.xyz.payment.controller;

import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

import com.xyz.payment.message.PaymentStatusProducer;
import com.xyz.payment.model.PaymentData;
import com.xyz.payment.service.PaymentGatewayService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    private PaymentGatewayService paymentGateway;
    private final PaymentStatusProducer paymentStatusProducer;

    public PaymentController(PaymentGatewayService paymentGateway, PaymentStatusProducer paymentStatusProducer) {
        this.paymentGateway = paymentGateway;
        this.paymentStatusProducer = paymentStatusProducer;
    }

    @PostMapping
    public ResponseEntity<PaymentData> pay(@Valid @RequestBody PaymentData data) {
        CompletableFuture<Boolean> future = paymentGateway.processPayment(data);
        future.whenCompleteAsync((res, throwable) -> {
            LOGGER.info(res.toString());
            data.setAcceped(res);
            data.setStatus(res ? "ACCEPTED" : "REFUSED");
            paymentStatusProducer.send(data);
        });
        data.setStatus("PROCESSING");
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

}