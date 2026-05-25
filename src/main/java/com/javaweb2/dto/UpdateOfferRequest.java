package com.javaweb2.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateOfferRequest {

    private String title;

    private String description;

    private BigDecimal price;
}