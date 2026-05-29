package com.javaweb2.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WorkerDTO {
    public Long id;

    public Long supplierId;

    public String name;

    public String email;

    public String password;

    public String role;

    public LocalDateTime createdAt;
}
