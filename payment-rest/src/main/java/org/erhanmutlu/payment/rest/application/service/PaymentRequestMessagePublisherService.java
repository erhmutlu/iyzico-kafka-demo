package org.erhanmutlu.payment.rest.application.service;

import org.erhanmutlu.payment.common.kafka.CreatePaymentRequestMessage;
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
        logger.info("publishing auth payment with conversationId: {}", createPaymentRequest.getConversationId());
        CreatePaymentRequestMessage createPaymentRequestMessage = convert(createPaymentRequest, PaymentType.AUTH);

        messageProducerService.write(paymentRequestTopic, createPaymentRequestMessage);

        return prepareResponse(createPaymentRequestMessage);
    }

    public CreatePaymentResponse publishPreAuthPayment(CreatePaymentRequest createPaymentRequest) {
        logger.info("publishing pre-auth payment with conversationId: {}", createPaymentRequest.getConversationId());
        CreatePaymentRequestMessage createPaymentRequestMessage = convert(createPaymentRequest, PaymentType.PRE_AUTH);

        messageProducerService.write(paymentRequestTopic, createPaymentRequestMessage);

        return prepareResponse(createPaymentRequestMessage);
    }

    public CreatePaymentResponse publishPostAuthPayment(CreatePaymentRequest createPaymentRequest) {
        logger.info("publishing post-auth payment with conversationId: {}", createPaymentRequest.getConversationId());
        CreatePaymentRequestMessage createPaymentRequestMessage = convert(createPaymentRequest, PaymentType.POST_AUTH);

        messageProducerService.write(paymentRequestTopic, createPaymentRequestMessage);

        return prepareResponse(createPaymentRequestMessage);
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

    private CreatePaymentResponse prepareResponse(CreatePaymentRequestMessage createPaymentRequestMessage){
        CreatePaymentResponse createPaymentResponse = new CreatePaymentResponse();
        createPaymentResponse.setIyzicoPaymentUniqueIdentifier(createPaymentRequestMessage.getUniqueId());
        createPaymentResponse.setConversationId(createPaymentRequestMessage.getConversationId());
        createPaymentResponse.setStatus("pending");
        createPaymentResponse.setSystemTime(Clock.systemUTC().millis());
        return createPaymentResponse;
    }
}
