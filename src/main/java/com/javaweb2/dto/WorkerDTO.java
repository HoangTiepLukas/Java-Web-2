package com.javaweb2.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WorkerDTO {
    private Long id;

    private Long supplierId;

    private String name;

    private String email;

    private String role;

    private LocalDateTime createdAt;
}
