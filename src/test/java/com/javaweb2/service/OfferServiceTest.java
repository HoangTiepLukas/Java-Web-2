package com.javaweb2.service;

import com.javaweb2.dto.CreateOfferRequest;
import com.javaweb2.dto.OfferDTO;
import com.javaweb2.entity.Customer;
import com.javaweb2.entity.Offer;
import com.javaweb2.entity.OfferStatus;
import com.javaweb2.entity.Supplier;
import com.javaweb2.repository.CustomerRepository;
import com.javaweb2.repository.OfferRepository;
import com.javaweb2.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    CustomerRepository customerRepository;
    SupplierRepository supplierRepository;
    OfferRepository offerRepository;
    OfferService offerService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        supplierRepository = mock(SupplierRepository.class);
        offerRepository = mock(OfferRepository.class);
        offerService = new OfferService(customerRepository, supplierRepository, offerRepository);
    }

    @Test
    void createOffer_createsAndReturnsDto() {
        Supplier s = new Supplier();
        s.setId(10L);
        s.setName("S10");

        Customer c = new Customer();
        c.setId(11L);
        c.setName("C11");

        when(supplierRepository.findById(10L)).thenReturn(Optional.of(s));
        when(customerRepository.findById(11L)).thenReturn(Optional.of(c));

        CreateOfferRequest req = new CreateOfferRequest();
        req.setTitle("Offer X");
        req.setDescription("desc");
        req.setPrice(new BigDecimal("50.00"));
        req.setSupplierId(10L);
        req.setCustomerId(11L);

        when(offerRepository.save(any(Offer.class))).thenAnswer(inv -> {
            Offer o = inv.getArgument(0);
            o.setId(20L);
            o.setCreatedAt(LocalDateTime.now());
            o.setStatus(OfferStatus.CREATED);
            return o;
        });

        OfferDTO dto = offerService.createOffer(req);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(20L);
        assertThat(dto.getTitle()).isEqualTo("Offer X");
        assertThat(dto.getSupplierId()).isEqualTo(10L);
        assertThat(dto.getCustomerId()).isEqualTo(11L);
        assertThat(dto.getStatus()).isEqualTo(OfferStatus.CREATED);

        System.out.println(dto.getId());
        System.out.println(dto.getTitle());
        System.out.println(dto.getSupplierId());
        System.out.println(dto.getCustomerId());
        System.out.println(dto.getStatus());
        System.out.println(dto.getCreatedAt());
    }
}
