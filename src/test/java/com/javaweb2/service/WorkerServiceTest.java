package com.javaweb2.service;

import com.javaweb2.dto.CreateWorkerRequest;
import com.javaweb2.dto.WorkerDTO;
import com.javaweb2.entity.Worker;
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
class WorkerServiceTest {

    WorkerRepository workerRepository;
    WorkerService workerService;

    @BeforeEach
    void setUp() {
        workerRepository = mock(WorkerRepository.class);
        workerService = new WorkerService(workerRepository);
    }

    @Test
    void createWorker_createsAndReturnsDto() {
        CreateWorkerRequest req = CreateWorkerRequest.builder()
                .name("Worker A")
                .email("w@worker.com")
                .password("pw")
                .role("role")
                .build();

        when(workerRepository.save(any(Worker.class))).thenAnswer(inv -> {
            Worker w = inv.getArgument(0);
            w.setId(3L);
            w.setCreatedAt(LocalDateTime.now());
            return w;
        });

        WorkerDTO dto = workerService.createWorker(req);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(3L);
        assertThat(dto.getName()).isEqualTo("Worker A");
        assertThat(dto.getRole()).isEqualTo("role");

        System.out.println(dto.getId());
        System.out.println(dto.getName());
        System.out.println(dto.getEmail());
        System.out.println(dto.getRole());
        System.out.println(dto.getCreatedAt());
    }
}
