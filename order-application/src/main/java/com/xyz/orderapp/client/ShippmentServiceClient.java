package com.xyz.orderapp.client;

import com.xyz.orderapp.model.ShipmentDataDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shipping-service", path = "/api/shipping")
public interface ShippmentServiceClient {

    @PostMapping
    ShipmentDataDto processShipment(@RequestBody ShipmentDataDto data);
}