package com.courier.tracking.customer.service;

import com.courier.tracking.customer.dto.CustomerRequest;
import com.courier.tracking.customer.entity.Customer;
import com.courier.tracking.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer registerCustomer(CustomerRequest request) {
        customerRepository.findByEmail(request.getEmail()).ifPresent(c -> {
            throw new IllegalArgumentException("Customer with email already exists: " + request.getEmail());
        });
        Customer customer = new Customer(request.getName(), request.getEmail(), request.getPhone(), request.getAddress());
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
