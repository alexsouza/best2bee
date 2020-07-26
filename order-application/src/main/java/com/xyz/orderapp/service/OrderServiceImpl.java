package com.xyz.orderapp.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.xyz.orderapp.client.PaymentServiceClient;
import com.xyz.orderapp.exception.ResourceNotFoundException;
import com.xyz.orderapp.model.Order;
import com.xyz.orderapp.model.PaymentDetails;
import com.xyz.orderapp.repository.OrderRepository;
import com.xyz.orderapp.repository.PaymentDetailsRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    public final ApplicationEventPublisher eventPublisher;
    private OrderRepository orderRepository;
    private PaymentDetailsRepository paymentDetailsRepository;
    private PaymentServiceClient paymentServiceClient;
    private OrderItemService orderItemService;

    public OrderServiceImpl(ApplicationEventPublisher eventPublisher, OrderRepository orderRepository,
            PaymentDetailsRepository paymentDetailsRepository, PaymentServiceClient paymentServiceClient,
            OrderItemService orderItemService) {
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
        this.paymentDetailsRepository = paymentDetailsRepository;
        this.paymentServiceClient = paymentServiceClient;
        this.orderItemService = orderItemService;
    }

    @Override
    public Iterable<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order get(@Min(value = 1, message = "Invalid order ID.") long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public Order create(@NotNull(message = "The order cannot be null.") @Valid Order order) {
        order.setDateCreated(LocalDate.now());

        calculateTotalAmount(order);
        paymentDetailsRepository.save(order.getPaymentDetails());

        orderRepository.save(order);
        eventPublisher.publishEvent(order);

        orderItemService.createAll(order.getOrderItems());

        callPaymentService(order);

        return order;
    }

    private void callPaymentService(Order order) {
        PaymentDetails details = order.getPaymentDetails();
        details.setExternalClientId(order.getId());
        PaymentDetails paymentResponse = paymentServiceClient.pay(details);

        details.setStatus(paymentResponse.getStatus());
        order.setStatus(paymentResponse.getStatus());

        paymentDetailsRepository.save(details);
        orderRepository.save(order);

        LOGGER.info(String.format("Dados da transação retornados : %s", paymentResponse));

    }

    private void calculateTotalAmount(Order order) {
        BigDecimal total = order.getOrderItems().stream().map((orderItem) -> new BigDecimal(orderItem.getTotalPrice()))
                .reduce(BigDecimal::add).get();
        PaymentDetails paymentDetails = order.getPaymentDetails();
        paymentDetails.setAmount(total);
    }

    @Override
    public void update(@NotNull(message = "The order cannot be null.") @Valid Order order) {
        LOGGER.info(String.format("Update and sending event order ==> %s", order));

        eventPublisher.publishEvent(order);
        orderRepository.save(order);
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }

}