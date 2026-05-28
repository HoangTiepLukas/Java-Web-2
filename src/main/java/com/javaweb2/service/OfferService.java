package com.javaweb2.service;

import com.javaweb2.dto.*;
import com.javaweb2.entity.Customer;
import com.javaweb2.entity.Offer;
import com.javaweb2.entity.OfferStatus;
import com.javaweb2.entity.Supplier;
import com.javaweb2.repository.CustomerRepository;
import com.javaweb2.repository.OfferRepository;
import com.javaweb2.repository.SupplierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfferService {
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    private final OfferRepository offerRepository;

    public OfferService(
        CustomerRepository customerRepository,
        SupplierRepository supplierRepository,
        OfferRepository offerRepository
    ) {
        this.customerRepository = customerRepository;
        this.supplierRepository = supplierRepository;
        this.offerRepository = offerRepository;
    }

    public OfferDTO createOffer(CreateOfferRequest request) {
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));
        Customer customer = customerRepository.findById(request.getCustomerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        Offer offer = new Offer();

        offer.setTitle(request.getTitle());
        offer.setDescription(request.getDescription());
        offer.setPrice(request.getPrice());

        offer.setSupplier(supplier);
        offer.setCustomer(customer);

        offer.setStatus(OfferStatus.CREATED);
        offer.setCreatedAt(LocalDateTime.now());

        Offer savedOffer = offerRepository.save(offer);

        return mapToDTO(savedOffer);
    }

    public List<OfferDTO> listOffers() {
        return offerRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public OfferDTO getOffer(Long id) {
        Offer offer = offerRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        return mapToDTO(offer);
    }

    public OfferDTO updateOffer(Long id, UpdateOfferRequest request) {
        Offer offer = offerRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));

        offer.setTitle(request.getTitle());
        offer.setDescription(request.getDescription());
        offer.setPrice(request.getPrice());

        Offer updatedOffer = offerRepository.save(offer);

        return mapToDTO(updatedOffer);
    }

    public OfferDTO acceptOffer(Long id, AcceptOfferRequest request) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));

        if (offer.getCustomer() == null || !offer.getCustomer().getId().equals(request.getCustomerId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer does not match offer");
        }

        if (offer.getStatus() != OfferStatus.CREATED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Offer can only be accepted from CREATED status");
        }

        offer.setStatus(OfferStatus.CUSTOMER_ACCEPTED);

        Offer acceptedOffer = offerRepository.save(offer);

        return mapToDTO(acceptedOffer);
    }

    public OfferDTO startWork(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));

        if (offer.getStatus() != OfferStatus.CUSTOMER_ACCEPTED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Work can only start if offer is customer accepted");
        }

        offer.setStatus(OfferStatus.IN_PROGRESS);

        Offer updatedOffer = offerRepository.save(offer);

        return mapToDTO(updatedOffer);
    }

    public OfferDTO deliverWork(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));

        if (offer.getStatus() != OfferStatus.IN_PROGRESS) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Offer must be in progress before delivery");
        }

        offer.setStatus(OfferStatus.DELIVERED);
        Offer updatedOffer = offerRepository.save(offer);

        return mapToDTO(updatedOffer);
    }

    public OfferDTO acceptDelivery(Long id, AcceptDeliveryRequest request) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));

        if (offer.getStatus() != OfferStatus.DELIVERED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Offer must be delivered before acceptance");
        }

        if (offer.getCustomer() == null || !offer.getCustomer().getId().equals(request.getCustomerId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer does not match offer");
        }

        offer.setStatus(OfferStatus.DELIVERY_ACCEPTED);

        Offer updatedOffer = offerRepository.save(offer);

        return mapToDTO(updatedOffer);
    }

    public OfferDTO invoiceOffer(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));

        if (offer.getStatus() != OfferStatus.DELIVERY_ACCEPTED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Offer can not be invoiced yet");
        }

        offer.setStatus(OfferStatus.INVOICED);
        Offer updatedOffer = offerRepository.save(offer);

        return mapToDTO(updatedOffer);
    }

    private OfferDTO mapToDTO(Offer offer) {
        return OfferDTO.builder()
                .id(offer.getId())
                .title(offer.getTitle())
                .description(offer.getDescription())
                .price(offer.getPrice())
                .customerId(offer.getCustomer() == null ? null : offer.getCustomer().getId())
                .supplierId(offer.getSupplier() == null ? null : offer.getSupplier().getId())
                .status(offer.getStatus())
                .createdAt(offer.getCreatedAt())
                .build();
    }
}
