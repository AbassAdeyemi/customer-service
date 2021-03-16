package com.hayba.customerservice.controller;

import com.hayba.customerservice.entity.Customer;
import com.hayba.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class CustomersController {
    private final CustomerRepository customerRepository;

    @GetMapping("/customers")
    public Flux<Customer> get() {
        return customerRepository.findAll();
    }
}
