package com.javaweb2.service;

import com.javaweb2.dto.CreateSupplierRequest;
import com.javaweb2.dto.SupplierDTO;
import com.javaweb2.dto.WorkerDTO;
import com.javaweb2.entity.Supplier;
import com.javaweb2.entity.Worker;
import com.javaweb2.repository.SupplierRepository;
import com.javaweb2.repository.WorkerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final WorkerRepository workerRepository;

    public SupplierService(SupplierRepository supplierRepository, WorkerRepository workerRepository) {
        this.supplierRepository = supplierRepository;
        this.workerRepository = workerRepository;
    }

    public SupplierDTO createSupplier(CreateSupplierRequest request) {
        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPassword(request.getPassword());
        supplier.setDescription(request.getDescription());
        supplier.setServices(request.getServices());
        supplier.setCreatedAt(LocalDateTime.now());

        Supplier createdSupplier = supplierRepository.save(supplier);

        return mapToDTO(createdSupplier);
    }

    public List<SupplierDTO> listSuppliers() {
        return supplierRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public SupplierDTO getSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));
        return mapToDTO(supplier);
    }

    public SupplierDTO addWorkerToSupplier(Long supplierId, Long workerId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found"));

        worker.setSupplier(supplier);

        workerRepository.save(worker);

        return mapToDTO(supplier);
    }

    public List<WorkerDTO> getAllWorkersBySupplier(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

        return supplier.getWorkers().stream()
                .map(this::mapWorkerToDTO)
                .toList();
    }

    private SupplierDTO mapToDTO(Supplier supplier) {
        return SupplierDTO.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .email(supplier.getEmail())
                .description(supplier.getDescription())
                .services(supplier.getServices())
                .createdAt(supplier.getCreatedAt())
                .build();
    }

    private WorkerDTO mapWorkerToDTO(Worker worker) {
        WorkerDTO workerDTO = new WorkerDTO();
        workerDTO.id = worker.getId();
        if (worker.getSupplier() != null) {
            workerDTO.supplierId = worker.getSupplier().getId();
        }
        workerDTO.name = worker.getName();
        workerDTO.email = worker.getEmail();
        workerDTO.role = worker.getRole();
        workerDTO.createdAt = worker.getCreatedAt();
        return workerDTO;
    }
}
