package com.javaweb2.controller;

import com.javaweb2.dto.CreateSupplierRequest;
import com.javaweb2.dto.SupplierDTO;
import com.javaweb2.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierDTO createSupplier(@Valid @RequestBody CreateSupplierRequest request) {
        return supplierService.createSupplier(request);
    }

    @GetMapping
    public List<SupplierDTO> listSupplier() {
        return supplierService.listSuppliers();
    }

    @GetMapping("/{id}")
    public SupplierDTO getSupplier(@PathVariable Long id) {
        return supplierService.getSupplier(id);
    }
}
