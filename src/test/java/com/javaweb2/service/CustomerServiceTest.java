package com.javaweb2.service;

import com.javaweb2.dto.CreateCustomerRequest;
import com.javaweb2.dto.CustomerDTO;
import com.javaweb2.entity.Customer;
import com.javaweb2.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    CustomerRepository customerRepository;
    CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void createCustomer_createsAndReturnsDto() {
        CreateCustomerRequest req = CreateCustomerRequest.builder()
                .name("Customer A")
                .email("c@customer.com")
                .password("pw")
                .build();

        when(customerRepository.save(any(Customer.class))).thenAnswer(inv -> {
            Customer c = inv.getArgument(0);
            c.setId(2L);
            c.setCreatedAt(LocalDateTime.now());
            return c;
        });

        CustomerDTO dto = customerService.createCustomer(req);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getName()).isEqualTo("Customer A");
        assertThat(dto.getEmail()).isEqualTo("c@customer.com");
        
        System.out.println(dto.getId());
        System.out.println(dto.getName());
        System.out.println(dto.getEmail());
        System.out.println(dto.getCreatedAt());

    }
}
