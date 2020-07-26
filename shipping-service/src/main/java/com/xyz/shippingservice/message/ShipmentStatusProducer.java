package com.xyz.shippingservice.message;

import java.util.UUID;

import com.xyz.shippingservice.model.ShipmentData;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class ShipmentStatusProducer {

    @Value("${message.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, ShipmentData> kafkaTemplate;

    public ShipmentStatusProducer(final KafkaTemplate<String, ShipmentData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(final @RequestBody ShipmentData data) {
        final String mensageKey = UUID.randomUUID().toString();
        kafkaTemplate.send(topicName, mensageKey, data);
    }
}