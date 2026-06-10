package com.javaweb2.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SupplierDTO {
    private Long id;

    private String name;

    private String email;

    private String description;

    private String services;

    private LocalDateTime createdAt;
}
