package com.javaweb2.service;

import com.javaweb2.dto.CreateSupplierRequest;
import com.javaweb2.dto.SupplierDTO;
import com.javaweb2.entity.Supplier;
import com.javaweb2.repository.SupplierRepository;
import com.javaweb2.repository.WorkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    SupplierRepository supplierRepository;
    WorkerRepository workerRepository;
    SupplierService supplierService;

    @BeforeEach
    void setUp() {
        supplierRepository = mock(SupplierRepository.class);
        workerRepository = mock(WorkerRepository.class);
        supplierService = new SupplierService(supplierRepository, workerRepository);
    }

    @Test
    void createSupplier_createsAndReturnsDto() {
        CreateSupplierRequest req = CreateSupplierRequest.builder()
                .name("Supplier A")
                .email("a@supplier.com")
                .password("pw")
                .description("desc")
                .services("svc")
                .build();

        when(supplierRepository.save(any(Supplier.class))).thenAnswer(inv -> {
            Supplier s = inv.getArgument(0);
            s.setId(1L);
            s.setCreatedAt(LocalDateTime.now());
            return s;
        });

        SupplierDTO dto = supplierService.createSupplier(req);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Supplier A");
        assertThat(dto.getEmail()).isEqualTo("a@supplier.com");
        assertThat(dto.getServices()).isEqualTo("svc");
        assertThat(dto.getCreatedAt()).isNotNull();

        System.out.println(dto.getId());
        System.out.println(dto.getName());
        System.out.println(dto.getEmail());
        System.out.println(dto.getServices());
        System.out.println(dto.getCreatedAt());
    }
}
