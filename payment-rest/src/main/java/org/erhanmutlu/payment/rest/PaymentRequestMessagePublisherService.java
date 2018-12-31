package org.erhanmutlu.payment.rest;

import org.erhanmutlu.kafkacommon.CreatePaymentRequestMessage;
import org.erhanmutlu.kafkacommon.PaymentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentRequestMessagePublisherService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentRequestMessagePublisherService.class);

    private CustomKafkaWriter kafkaWriter;

    public PaymentRequestMessagePublisherService(CustomKafkaWriter kafkaWriter) {
        this.kafkaWriter = kafkaWriter;
    }

    public void publishAuthPayment(CreatePaymentRequest createPaymentRequest) {
        CreatePaymentRequestMessage createPaymentRequestMessage = convert(createPaymentRequest, PaymentType.AUTH);
        logger.info("message is prepared");
        kafkaWriter.write("TopicY", createPaymentRequestMessage);
    }

    public CreatePaymentRequestMessage convert(CreatePaymentRequest createPaymentRequest, PaymentType paymentType) {
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
