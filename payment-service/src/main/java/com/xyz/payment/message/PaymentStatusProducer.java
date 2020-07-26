package com.xyz.payment.message;

import java.util.UUID;

import com.xyz.payment.model.PaymentData;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class PaymentStatusProducer {

    @Value("${message.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, PaymentData> kafkaTemplate;

    public PaymentStatusProducer(final KafkaTemplate<String, PaymentData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(final @RequestBody PaymentData data) {
        final String mensageKey = UUID.randomUUID().toString();
        kafkaTemplate.send(topicName, mensageKey, data);
    }
}