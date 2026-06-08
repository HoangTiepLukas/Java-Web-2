package com.javaweb2;

import com.javaweb2.dto.AcceptDeliveryRequest;
import com.javaweb2.dto.AcceptOfferRequest;
import com.javaweb2.dto.CreateCustomerRequest;
import com.javaweb2.dto.CreateInvoiceRequest;
import com.javaweb2.dto.CreateOfferRequest;
import com.javaweb2.dto.CreateSupplierRequest;
import com.javaweb2.dto.CreateWorkerRequest;
import com.javaweb2.dto.CustomerDTO;
import com.javaweb2.dto.InvoiceDTO;
import com.javaweb2.dto.OfferDTO;
import com.javaweb2.dto.SupplierDTO;
import com.javaweb2.dto.WorkerDTO;
import com.javaweb2.entity.Invoice;
import com.javaweb2.entity.Offer;
import com.javaweb2.entity.OfferStatus;
import com.javaweb2.entity.Worker;
import com.javaweb2.repository.InvoiceRepository;
import com.javaweb2.repository.OfferRepository;
import com.javaweb2.repository.WorkerRepository;
import com.javaweb2.service.CustomerService;
import com.javaweb2.service.InvoiceService;
import com.javaweb2.service.OfferService;
import com.javaweb2.service.SupplierService;
import com.javaweb2.service.WorkerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class Javaweb2ApplicationTests {



	@Autowired
	private WorkerRepository workerRepository;

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private WorkerService workerService;

	@Autowired
	private OfferService offerService;

	@Autowired
	private InvoiceService invoiceService;


	@Test
	void Workflow() {
		SupplierDTO createdSupplier = supplierService.createSupplier(createSupplierRequest());
		CustomerDTO createdCustomer = customerService.createCustomer(createCustomerRequest());
		WorkerDTO createdWorker = workerService.createWorker(createWorkerRequest());

		assertThat(createdSupplier.getId()).isNotNull();
		assertThat(createdCustomer.getId()).isNotNull();
		assertThat(createdWorker.getId()).isNotNull();

		SupplierDTO supplierWithWorker = supplierService.addWorkerToSupplier(createdSupplier.getId(), createdWorker.getId());
		assertThat(supplierWithWorker.getId()).isEqualTo(createdSupplier.getId());

		List<Worker> workers = workerRepository.findAll();
		assertThat(workers).hasSize(1);
		assertThat(workers.getFirst().getSupplier()).isNotNull();
		assertThat(workers.getFirst().getSupplier().getId()).isEqualTo(createdSupplier.getId());

		CreateOfferRequest offerRequest = createOfferRequest(createdSupplier.getId(), createdCustomer.getId());

		OfferDTO createdOffer = offerService.createOffer(offerRequest);
		assertThat(createdOffer.getId()).isNotNull();
		assertThat(createdOffer.getStatus()).isEqualTo(OfferStatus.CREATED);

		Offer offerBeforeAcceptance = offerRepository.findById(createdOffer.getId()).orElseThrow();
		assertThat(offerBeforeAcceptance.getSupplier().getId()).isEqualTo(createdSupplier.getId());
		assertThat(offerBeforeAcceptance.getCustomer().getId()).isEqualTo(createdCustomer.getId());

		AcceptOfferRequest acceptOfferRequest = new AcceptOfferRequest();
		acceptOfferRequest.setCustomerId(createdCustomer.getId());
		OfferDTO acceptedOffer = offerService.acceptOffer(createdOffer.getId(), acceptOfferRequest);
		assertThat(acceptedOffer.getStatus()).isEqualTo(OfferStatus.CUSTOMER_ACCEPTED);

		OfferDTO inProgressOffer = offerService.startWork(createdOffer.getId());
		assertThat(inProgressOffer.getStatus()).isEqualTo(OfferStatus.IN_PROGRESS);

		OfferDTO deliveredOffer = offerService.deliverWork(createdOffer.getId());
		assertThat(deliveredOffer.getStatus()).isEqualTo(OfferStatus.DELIVERED);

		AcceptDeliveryRequest acceptDeliveryRequest = new AcceptDeliveryRequest();
		acceptDeliveryRequest.setCustomerId(createdCustomer.getId());
		OfferDTO deliveryAcceptedOffer = offerService.acceptDelivery(createdOffer.getId(), acceptDeliveryRequest);
		assertThat(deliveryAcceptedOffer.getStatus()).isEqualTo(OfferStatus.DELIVERY_ACCEPTED);

		OfferDTO invoicedOffer = offerService.invoiceOffer(createdOffer.getId());
		assertThat(invoicedOffer.getStatus()).isEqualTo(OfferStatus.INVOICED);

		CreateInvoiceRequest invoiceRequest = createInvoiceRequest(createdOffer.getId(), createdCustomer.getId(), createdSupplier.getId());

		InvoiceDTO createdInvoice = invoiceService.createInvoice(invoiceRequest);
		assertThat(createdInvoice.getId()).isNotNull();
		assertThat(createdInvoice.getOfferId()).isEqualTo(createdOffer.getId());
		assertThat(createdInvoice.getCustomerId()).isEqualTo(createdCustomer.getId());
		assertThat(createdInvoice.getSupplierId()).isEqualTo(createdSupplier.getId());

		Invoice invoice = invoiceRepository.findById(createdInvoice.getId()).orElseThrow();
		assertThat(invoice.getOffer().getId()).isEqualTo(createdOffer.getId());
		assertThat(invoice.getAmount()).isEqualByComparingTo("100.00");
	}

	private CreateSupplierRequest createSupplierRequest() {
		return CreateSupplierRequest.builder()
				.name("Supplier A")
				.email("supplier-a@example.com")
				.password("password")
				.description("supplier desc")
				.services("service")
				.build();
	}

	private CreateCustomerRequest createCustomerRequest() {
		return CreateCustomerRequest.builder()
				.name("Customer A")
				.email("customer-a@example.com")
				.password("password")
				.build();
	}

	private CreateWorkerRequest createWorkerRequest() {
		return CreateWorkerRequest.builder()
				.name("Worker A")
				.email("worker-a@example.com")
				.password("password")
				.role("worker")
				.build();
	}

	private CreateOfferRequest createOfferRequest(Long supplierId, Long customerId) {
		CreateOfferRequest request = new CreateOfferRequest();
		request.setTitle("Offer A");
		request.setDescription("offer desc");
		request.setPrice(new BigDecimal("100.00"));
		request.setSupplierId(supplierId);
		request.setCustomerId(customerId);
		return request;
	}

	private CreateInvoiceRequest createInvoiceRequest(Long offerId, Long customerId, Long supplierId) {
		CreateInvoiceRequest request = new CreateInvoiceRequest();
		request.setOfferId(offerId);
		request.setCustomerId(customerId);
		request.setSupplierId(supplierId);
		request.setInvoiceNumber(1001L);
		request.setAmount(new BigDecimal("100.00"));
		request.setIssuedAt(LocalDate.now());
		request.setDueDate(LocalDate.now().plusDays(7));
		return request;
	}

}
