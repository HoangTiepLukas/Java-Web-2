package com.javaweb2.dto;

import com.javaweb2.entity.InvoiceStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

import java.time.LocalDateTime;

public class InvoiceDTO {
    public Long id;

    public Long offerId;

    public Long customerId;

    public Long supplierId;

    public Long invoiceNumber;

    public BigDecimal amount;

    public LocalDate issuedAt;

    public LocalDate dueDate;

    public String status;

    public LocalDateTime createdAt;
}
