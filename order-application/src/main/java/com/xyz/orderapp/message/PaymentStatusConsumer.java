package com.xyz.orderapp.message;

import java.util.stream.Collectors;

import com.xyz.orderapp.client.ShippmentServiceClient;
import com.xyz.orderapp.model.AddressDto;
import com.xyz.orderapp.model.Order;
import com.xyz.orderapp.model.PaymentDetails;
import com.xyz.orderapp.model.ProductDataDto;
import com.xyz.orderapp.model.ShipmentDataDto;
import com.xyz.orderapp.repository.PaymentDetailsRepository;
import com.xyz.orderapp.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentStatusConsumer {

    private PaymentDetailsRepository paymentDetailsRepository;
    private OrderService orderService;
    private ShippmentServiceClient shippmentServiceClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentStatusConsumer.class);

    public PaymentStatusConsumer(PaymentDetailsRepository paymentDetailsRepository, OrderService orderService,
            ShippmentServiceClient shippmentServiceClient) {
        this.paymentDetailsRepository = paymentDetailsRepository;
        this.orderService = orderService;
        this.shippmentServiceClient = shippmentServiceClient;
    }

    @KafkaListener(topics = "${message.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(PaymentDetails data) {
        LOGGER.info("Payment detail: " + data);

        Order order = orderService.get(data.getExternalClientId());
        order.setStatus(String.format("ORDER_PAYMENT_%s", data.getStatus()));
        orderService.update(order);

        PaymentDetails paymentDetails = order.getPaymentDetails();
        paymentDetails.setStatus(data.getStatus());
        paymentDetailsRepository.save(paymentDetails);

        if (data.getAcceped()) {
            startShipmentProcess(order);
        }

    }

    private void startShipmentProcess(Order order) {
        ShipmentDataDto shipmentData = populateShipmentData(order);
        shipmentData = shippmentServiceClient.processShipment(shipmentData);

        LOGGER.info("Shipment data: " + shipmentData);

        order.setStatus(String.format("ORDER_%s", shipmentData.getStatus()));
        orderService.update(order);
    }

    private ShipmentDataDto populateShipmentData(Order order) {
        ShipmentDataDto data = new ShipmentDataDto();
        data.setExternalClientId(order.getId().toString());
        data.setProducts(order.getOrderItems().stream().map(orderItem -> {
            ProductDataDto productDataDto = new ProductDataDto();
            productDataDto.setName(orderItem.getProduct().getName());
            productDataDto.setQuantity(orderItem.getQuantity());
            return productDataDto;
        }).collect(Collectors.toList()));

        AddressDto adressData = new AddressDto();
        adressData.setCity(order.getPaymentDetails().getCity());
        adressData.setNumber(order.getPaymentDetails().getNumber());
        adressData.setStreet(order.getPaymentDetails().getStreet());

        data.setAddress(adressData);

        return data;
    }

}
