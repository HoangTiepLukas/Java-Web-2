package com.javaweb2.dto;

import com.javaweb2.entity.OfferStatus;
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

    private Long customerId;

    private Long supplierId;

    private OfferStatus status;

    private LocalDateTime createdAt;
}
