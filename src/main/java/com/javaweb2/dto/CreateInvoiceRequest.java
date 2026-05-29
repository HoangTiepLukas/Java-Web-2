package com.javaweb2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class CreateInvoiceRequest {

    @NotNull
    private Long offerId;

    @NotNull
    private Long customerId;

    @NotNull
    private Long supplierId;

    @Positive
    private Long invoiceNumber;

    @Positive
    private BigDecimal amount;

    private LocalDate issuedAt;

    private LocalDate dueDate;
}
