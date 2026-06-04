package com.javaweb2.service;

import com.javaweb2.dto.CreateInvoiceRequest;
import com.javaweb2.entity.Customer;
import com.javaweb2.entity.Invoice;
import com.javaweb2.entity.Offer;
import com.javaweb2.entity.Supplier;
import com.javaweb2.repository.CustomerRepository;
import com.javaweb2.repository.InvoiceRepository;
import com.javaweb2.repository.OfferRepository;
import com.javaweb2.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    InvoiceRepository invoiceRepository;
    OfferRepository offerRepository;
    CustomerRepository customerRepository;
    SupplierRepository supplierRepository;
    InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        invoiceRepository = mock(InvoiceRepository.class);
        offerRepository = mock(OfferRepository.class);
        customerRepository = mock(CustomerRepository.class);
        supplierRepository = mock(SupplierRepository.class);

        invoiceService = new InvoiceService(invoiceRepository, offerRepository, customerRepository, supplierRepository);
    }

    @Test
    void createInvoice_createsAndReturnsDto() {
        Supplier s = new Supplier();
        s.setId(100L);

        Customer c = new Customer();
        c.setId(200L);

        Offer o = new Offer();
        o.setId(300L);
        o.setSupplier(s);
        o.setCustomer(c);

        when(offerRepository.findById(300L)).thenReturn(Optional.of(o));
        when(customerRepository.findById(200L)).thenReturn(Optional.of(c));
        when(supplierRepository.findById(100L)).thenReturn(Optional.of(s));

        CreateInvoiceRequest req = new CreateInvoiceRequest();
        req.setOfferId(300L);
        req.setCustomerId(200L);
        req.setSupplierId(100L);
        req.setInvoiceNumber(123L);
        req.setAmount(new BigDecimal("75.00"));
        req.setIssuedAt(LocalDate.now());
        req.setDueDate(LocalDate.now().plusDays(14));

        when(invoiceRepository.save(any(Invoice.class))).thenAnswer(inv -> {
            Invoice i = inv.getArgument(0);
            i.setId(400L);
            i.setCreatedAt(LocalDateTime.now());
            return i;
        });

        var dto = invoiceService.createInvoice(req);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(400L);
        assertThat(dto.getOfferId()).isEqualTo(300L);
        assertThat(dto.getCustomerId()).isEqualTo(200L);
        assertThat(dto.getSupplierId()).isEqualTo(100L);

        System.out.println(dto.getId());
        System.out.println(dto.getOfferId());
        System.out.println(dto.getCustomerId());
        System.out.println(dto.getSupplierId());
        System.out.println(dto.getCreatedAt());
    }
}
