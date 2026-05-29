package com.javaweb2.controller;

import com.javaweb2.dto.CreateInvoiceRequest;
import com.javaweb2.dto.InvoiceDTO;
import com.javaweb2.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

	private final InvoiceService invoiceService;

	public InvoiceController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public InvoiceDTO createInvoice(@Valid @RequestBody CreateInvoiceRequest request) {
		return invoiceService.createInvoice(request);
	}

	@GetMapping
	public List<InvoiceDTO> listInvoices() {
		return invoiceService.listInvoices();
	}

	@GetMapping("/{id}")
	public InvoiceDTO getInvoice(@PathVariable Long id) {
		return invoiceService.getInvoice(id);
	}
}
