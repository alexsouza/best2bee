package com.xyz.orderapp.repository;

import com.xyz.orderapp.model.PaymentDetails;

import org.springframework.data.repository.CrudRepository;

public interface PaymentDetailsRepository extends CrudRepository<PaymentDetails, Long> {

}