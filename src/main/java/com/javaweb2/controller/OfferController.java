package com.javaweb2.controller;

import com.javaweb2.dto.*;
import com.javaweb2.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OfferDTO createOffer(@Valid @RequestBody CreateOfferRequest request) {

        return offerService.createOffer(request);
    }

    @GetMapping
    public List<OfferDTO> listOffers() {

        return offerService.listOffers();
    }

    @GetMapping("/{id}")
    public OfferDTO getOffer(@PathVariable Long id) {

        return offerService.getOffer(id);
    }

    @PatchMapping("/{id}")
    public OfferDTO updateOffer(
            @PathVariable Long id,
            @Valid
            @RequestBody UpdateOfferRequest request
    ) {
        return offerService.updateOffer(id, request);
    }

    @PostMapping("/{id}/accept-offer")
    public OfferDTO acceptOffer(
            @PathVariable Long id,
            @Valid
            @RequestBody AcceptOfferRequest request
    ) {
        return offerService.acceptOffer(id, request);
    }

    @PostMapping("/{id}/start-work")
    public OfferDTO startWork(@PathVariable Long id) {

        return offerService.startWork(id);
    }

    @PostMapping("/{id}/deliver")
    public OfferDTO deliverWork(@PathVariable Long id) {

        return offerService.deliverWork(id);
    }

    @PostMapping("/{id}/accept-delivery")
    public OfferDTO acceptDelivery(
            @PathVariable Long id,
            @Valid
            @RequestBody AcceptDeliveryRequest request
    ) {
        return offerService.acceptDelivery(id, request);
    }

    @PostMapping("/{id}/invoice")
    public OfferDTO invoiceOffer(@PathVariable Long id) {
        return offerService.invoiceOffer(id);
    }
}