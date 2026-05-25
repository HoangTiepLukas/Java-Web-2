package com.javaweb2.service;

import com.javaweb2.dto.CreateSupplierRequest;
import com.javaweb2.dto.SupplierDTO;
import com.javaweb2.entity.Supplier;
import com.javaweb2.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public SupplierDTO createSupplier(CreateSupplierRequest request) {
        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPassword(request.getPassword());
        supplier.setCreated_at(LocalDateTime.now());

        Supplier createdSupplier = supplierRepository.save(supplier);

        return mapToDTO(createdSupplier);
    }

    public List<SupplierDTO> listSuppliers() {
        return supplierRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public SupplierDTO getSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));

        return mapToDTO(supplier);
    }

    private SupplierDTO mapToDTO(Supplier supplier) {

        return SupplierDTO.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .email(supplier.getEmail())
                .created_at(supplier.getCreated_at())
                .build();
    }
}
