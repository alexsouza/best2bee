package com.xyz.orderapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.xyz.orderapp.dto.OrderItemDto;
import com.xyz.orderapp.model.Customer;
import com.xyz.orderapp.model.Order;
import com.xyz.orderapp.model.OrderItem;
import com.xyz.orderapp.model.PaymentDetails;
import com.xyz.orderapp.service.OrderItemService;
import com.xyz.orderapp.service.OrderService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
    }

    @GetMapping(value = { "", "/" })
    public @NotNull Iterable<Order> getOrders() {
        return orderService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Order getOrder(@PathVariable("id") Long id) {
        return orderService.get(id);
    }

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody OrderData data) {
        Order order = new Order();
        order.setCustomer(data.getCustomer());
        order.setPaymentDetails(data.getPaymentDetails());

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto orderItem : data.getOrderItems()) {
            orderItems.add(new OrderItem(order, orderItem.getProduct(), orderItem.getQuantity()));
        }
        order.setOrderItems(orderItems);
        order.setStatus("ORDER_CREATED");

        order = orderService.create(order);

        String uri = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/order/{id}")
                .buildAndExpand(order.getId()).toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        return new ResponseEntity<>(order, headers, HttpStatus.CREATED);
    }

    public static class OrderData {

        @NotNull(message = "Order must have a customer")
        private @Valid Customer customer;

        @NotNull(message = "Order must have payment details")
        private @Valid PaymentDetails paymentDetails;

        @NotEmpty(message = "Order must have at least one item")
        private @Valid List<OrderItemDto> orderItems;

        public List<OrderItemDto> getOrderItems() {
            return orderItems;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public void setOrderItems(List<OrderItemDto> orderItems) {
            this.orderItems = orderItems;
        }

        public PaymentDetails getPaymentDetails() {
            return paymentDetails;
        }

        public void setPaymentDetails(PaymentDetails paymentDetails) {
            this.paymentDetails = paymentDetails;
        }

    }

}