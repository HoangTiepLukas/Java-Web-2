package com.javaweb2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "offerId", nullable = false)
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "supplierId", nullable = false)
    private Supplier supplier;

    @Column(nullable = false)
    private Long invoiceNumber;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "issuedAt", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "dueDate", nullable = false)
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
}
