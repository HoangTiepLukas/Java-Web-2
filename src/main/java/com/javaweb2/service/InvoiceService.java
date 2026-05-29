package com.javaweb2.service;

import com.javaweb2.dto.CreateInvoiceRequest;
import com.javaweb2.dto.InvoiceDTO;
import com.javaweb2.entity.Customer;
import com.javaweb2.entity.Invoice;
import com.javaweb2.entity.InvoiceStatus;
import com.javaweb2.entity.Offer;
import com.javaweb2.entity.Supplier;
import com.javaweb2.repository.CustomerRepository;
import com.javaweb2.repository.InvoiceRepository;
import com.javaweb2.repository.OfferRepository;
import com.javaweb2.repository.SupplierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class InvoiceService {

	private final InvoiceRepository invoiceRepository;
	private final OfferRepository offerRepository;
	private final CustomerRepository customerRepository;
	private final SupplierRepository supplierRepository;

	public InvoiceService(
			InvoiceRepository invoiceRepository,
			OfferRepository offerRepository,
			CustomerRepository customerRepository,
			SupplierRepository supplierRepository
	) {
		this.invoiceRepository = invoiceRepository;
		this.offerRepository = offerRepository;
		this.customerRepository = customerRepository;
		this.supplierRepository = supplierRepository;
	}

	public InvoiceDTO createInvoice(CreateInvoiceRequest request) {
		Offer offer = offerRepository.findById(request.getOfferId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
		Customer customer = customerRepository.findById(request.getCustomerId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
		Supplier supplier = supplierRepository.findById(request.getSupplierId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

		if (offer.getCustomer() == null || offer.getSupplier() == null
				|| !offer.getCustomer().getId().equals(customer.getId())
				|| !offer.getSupplier().getId().equals(supplier.getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invoice parties must match the offer");
		}

		Invoice invoice = new Invoice();
		invoice.setOffer(offer);
		invoice.setCustomer(customer);
		invoice.setSupplier(supplier);
		invoice.setInvoiceNumber(request.getInvoiceNumber());
		invoice.setAmount(request.getAmount());
		invoice.setIssuedAt(toLocalDateTime(request.getIssuedAt()));
		invoice.setDueDate(toLocalDateTime(request.getDueDate()));
		invoice.setStatus(InvoiceStatus.CREATED);
		invoice.setCreatedAt(LocalDateTime.now());

		return mapToDTO(invoiceRepository.save(invoice));
	}

	public List<InvoiceDTO> listInvoices() {
		return invoiceRepository.findAll().stream().map(this::mapToDTO).toList();
	}

	public InvoiceDTO getInvoice(Long id) {
		Invoice invoice = invoiceRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));
		return mapToDTO(invoice);
	}

	private InvoiceDTO mapToDTO(Invoice invoice) {
		return InvoiceDTO.builder()
				.id(invoice.getId())
				.customerId(invoice.getCustomer().getId())
				.supplierId(invoice.getSupplier().getId())
				.offerId(invoice.getOffer().getId())
				.invoiceNumber(invoice.getInvoiceNumber())
				.amount(invoice.getAmount())
				.issuedAt(invoice.getIssuedAt())
				.dueDate(invoice.getDueDate())
				.status(invoice.getStatus())
				.createdAt(invoice.getCreatedAt())
				.build();
	}

	private LocalDateTime toLocalDateTime(LocalDate date) {
		return date.atStartOfDay(ZoneOffset.UTC).toLocalDateTime();
	}


}
