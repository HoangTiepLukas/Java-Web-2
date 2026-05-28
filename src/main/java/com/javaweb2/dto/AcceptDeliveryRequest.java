package com.javaweb2.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptDeliveryRequest {

    @NotNull
    private Long customerId;
}