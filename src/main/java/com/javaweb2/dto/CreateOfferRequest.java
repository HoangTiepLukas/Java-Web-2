package com.javaweb2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateOfferRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private Long supplierId;

    @NotNull
    private Long customerId;
}