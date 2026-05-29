package com.javaweb2.dto;

import com.javaweb2.entity.InvoiceStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.time.LocalDateTime;

@Getter
@Builder
public class InvoiceDTO {
    public Long id;

    public Long offerId;

    public Long customerId;

    public Long supplierId;

    public Long invoiceNumber;

    public BigDecimal amount;

    public LocalDate issuedAt;

    public LocalDate dueDate;

    public InvoiceStatus status;

    public LocalDateTime createdAt;
}
