package org.erhanmutlu.payment.rest.application.service;

import org.erhanmutlu.kafkacommon.CreatePaymentRequestMessage;
import org.erhanmutlu.kafkacommon.PaymentType;
import org.erhanmutlu.payment.rest.infrastructure.kafka.KafkaProducerService;
import org.erhanmutlu.payment.rest.application.request.CreatePaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentRequestMessagePublisherService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentRequestMessagePublisherService.class);

    @Value("${kafka.topics.payment:t_payment}")
    public String paymentRequestTopic;

    private KafkaProducerService kafkaWriter;

    public PaymentRequestMessagePublisherService(KafkaProducerService kafkaWriter) {
        this.kafkaWriter = kafkaWriter;
    }

    public void publishAuthPayment(CreatePaymentRequest createPaymentRequest) {
        CreatePaymentRequestMessage createPaymentRequestMessage = convert(createPaymentRequest, PaymentType.AUTH);
        logger.info("message is prepared");
        kafkaWriter.write(paymentRequestTopic, createPaymentRequestMessage);
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
