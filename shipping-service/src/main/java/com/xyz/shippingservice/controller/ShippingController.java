package com.xyz.shippingservice.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

import com.xyz.shippingservice.message.ShipmentStatusProducer;
import com.xyz.shippingservice.model.ShipmentData;
import com.xyz.shippingservice.service.ShippingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingController.class);

    private ShippingService shippingService;
    private final ShipmentStatusProducer shipmentStatusProducer;

    public ShippingController(ShippingService shippingService, ShipmentStatusProducer shipmentStatusProducer) {
        this.shippingService = shippingService;
        this.shipmentStatusProducer = shipmentStatusProducer;
    }

    @GetMapping
    public String shippPing() {
        LOGGER.info("Shipping test");
        return "Shipping";
    }

    @PostMapping
    public ResponseEntity<ShipmentData> startProcess(@Valid @RequestBody ShipmentData data) {
        CompletableFuture<Boolean> future = shippingService.startProcess();
        future.whenCompleteAsync((res, throwable) -> {
            LOGGER.info(res.toString());
            data.setStatus(res ? "SHIPPED" : "DELAYED_SHIPMENT");
            // fake track id
            data.setTrackId(UUID.randomUUID().toString());
            shipmentStatusProducer.send(data);
        });
        data.setStatus("PROCESSING_SHIPMENT");
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

}