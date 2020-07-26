package com.xyz.orderapp.message;

import com.xyz.orderapp.model.Order;
import com.xyz.orderapp.model.ShipmentDataDto;
import com.xyz.orderapp.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ShipmentStatusConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentStatusConsumer.class);

    private OrderService orderService;

    public ShipmentStatusConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "${message.topic.shipment.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(ShipmentDataDto data) {
        LOGGER.info("Shipment data consumed: " + data);

        Order order = orderService.get(Long.parseLong(data.getExternalClientId()));
        order.setTrackId(data.getTrackId());
        order.setStatus(String.format("ORDER_%s", data.getStatus()));

        orderService.update(order);
    }

}