package com.javaweb2.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class OfferDTO {

    private Long id;

    private String title;

    private String description;

    private BigDecimal price;

    private String status;

    private String supplierName;

    private String customerName;

    private LocalDateTime createdAt;
}
