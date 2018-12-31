package org.erhanmutlu.payment.rest;

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
    public String auth(@RequestBody @Valid CreatePaymentRequest createPaymentRequest) {
//        paymentRequestMessagePublisherService.publishAuthPayment(createPaymentRequest);
        return "OK";
    }
}
