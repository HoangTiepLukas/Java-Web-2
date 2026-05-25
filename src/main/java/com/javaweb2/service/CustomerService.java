package com.javaweb2.service;

import com.javaweb2.dto.CreateCustomerRequest;
import com.javaweb2.dto.CustomerDTO;
import com.javaweb2.entity.Customer;
import com.javaweb2.repository.CustomerRepository;
import org.springframework.stereotype.Service;

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
        customer.setCreated_at(LocalDateTime.now());

        Customer createdCustomer = customerRepository.save(customer);

        return mapToDTO(createdCustomer);
    }

    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public CustomerDTO getCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));

        return mapToDTO(customer);
    }

    private CustomerDTO mapToDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .created_at(customer.getCreated_at())
                .build();
    }
}
