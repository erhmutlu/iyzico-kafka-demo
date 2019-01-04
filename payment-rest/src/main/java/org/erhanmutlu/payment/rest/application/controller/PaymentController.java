package org.erhanmutlu.payment.rest.application.controller;

import org.erhanmutlu.payment.rest.application.request.PaymentAuthRequest;
import org.erhanmutlu.payment.rest.application.request.PaymentPostAuthRequest;
import org.erhanmutlu.payment.rest.application.request.PaymentPreAuthRequest;
import org.erhanmutlu.payment.rest.application.response.CreatePaymentResponse;
import org.erhanmutlu.payment.rest.application.service.PaymentRequestMessagePublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PaymentController {

    private final PaymentRequestMessagePublisherService paymentRequestMessagePublisherService;

    public PaymentController(PaymentRequestMessagePublisherService paymentRequestMessagePublisherService) {
        this.paymentRequestMessagePublisherService = paymentRequestMessagePublisherService;
    }

    @PostMapping(path = "/api/payment/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreatePaymentResponse auth(@RequestBody @Valid PaymentAuthRequest paymentAuthRequest) {
        return paymentRequestMessagePublisherService.publishAuthPayment(paymentAuthRequest);
    }

    @PostMapping(path = "/api/payment/pre-auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreatePaymentResponse preAuth(@RequestBody @Valid PaymentPreAuthRequest paymentPreAuthRequest) {
        return paymentRequestMessagePublisherService.publishPreAuthPayment(paymentPreAuthRequest);
    }

    @PostMapping(path = "/api/payment/post-auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreatePaymentResponse postAuth(@RequestBody @Valid PaymentPostAuthRequest paymentPostAuthRequest) {
        return paymentRequestMessagePublisherService.publishPostAuthPayment(paymentPostAuthRequest);
    }
}
