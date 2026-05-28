package com.javaweb2.service;

import com.javaweb2.dto.CreateCustomerRequest;
import com.javaweb2.dto.CustomerDTO;
import com.javaweb2.entity.Customer;
import com.javaweb2.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO createCustomer(CreateCustomerRequest request) {
        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        customer.setCreatedAt(LocalDateTime.now());

        Customer createdCustomer = customerRepository.save(customer);

        return mapToDTO(createdCustomer);
    }

    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public CustomerDTO getCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        return mapToDTO(customer);
    }

    private CustomerDTO mapToDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .createdAt(customer.getCreatedAt())
                .build();
    }
}
