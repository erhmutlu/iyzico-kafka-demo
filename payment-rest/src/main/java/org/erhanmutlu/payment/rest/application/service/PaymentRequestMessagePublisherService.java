package org.erhanmutlu.payment.rest.application.service;

import org.erhanmutlu.payment.common.CreatePaymentRequestMessage;
import org.erhanmutlu.payment.common.PaymentType;
import org.erhanmutlu.payment.rest.application.response.CreatePaymentResponse;
import org.erhanmutlu.payment.rest.infrastructure.kafka.MessageProducerService;
import org.erhanmutlu.payment.rest.application.request.CreatePaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.UUID;

@Service
public class PaymentRequestMessagePublisherService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentRequestMessagePublisherService.class);

    @Value("${kafka.topics.payment:t_payment}")
    public String paymentRequestTopic;

    private MessageProducerService messageProducerService;

    public PaymentRequestMessagePublisherService(MessageProducerService messageProducerService) {
        this.messageProducerService = messageProducerService;
    }

    public CreatePaymentResponse publishAuthPayment(CreatePaymentRequest createPaymentRequest) {
        CreatePaymentRequestMessage createPaymentRequestMessage = convert(createPaymentRequest, PaymentType.AUTH);
        logger.info("message is prepared");
        messageProducerService.write(paymentRequestTopic, createPaymentRequestMessage);

        CreatePaymentResponse createPaymentResponse = new CreatePaymentResponse();
        createPaymentResponse.setIyzicoPaymentUniqueIdentifier(createPaymentRequestMessage.getUniqueId());
        createPaymentResponse.setConversationId(createPaymentRequestMessage.getConversationId());
        createPaymentResponse.setStatus("pending");
        createPaymentResponse.setSystemTime(Clock.systemUTC().millis());
        return createPaymentResponse;
    }

    private CreatePaymentRequestMessage convert(CreatePaymentRequest createPaymentRequest, PaymentType paymentType) {
        CreatePaymentRequestMessage createPaymentRequestMessage = new CreatePaymentRequestMessage();
        createPaymentRequestMessage.setPaymentType(paymentType);
        createPaymentRequestMessage.setApiKey(createPaymentRequest.getApiKey());
        createPaymentRequestMessage.setSecretKey(createPaymentRequest.getSecretKey());
        createPaymentRequestMessage.setConversationId(createPaymentRequest.getConversationId());
        createPaymentRequestMessage.setPrice(new BigDecimal(createPaymentRequest.getPrice()));
        createPaymentRequestMessage.setUniqueId(UUID.randomUUID().toString());
        return createPaymentRequestMessage;
    }
}
