package com.javaweb2.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CustomerDTO {
    private Long id;

    private String name;

    private String email;

    private String password;

    private LocalDateTime createdAt;
}
