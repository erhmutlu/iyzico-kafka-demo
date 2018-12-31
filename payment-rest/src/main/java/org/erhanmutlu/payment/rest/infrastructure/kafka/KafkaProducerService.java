package org.erhanmutlu.payment.rest.infrastructure.kafka;

import org.erhanmutlu.kafkacommon.CreatePaymentRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional("kafkaTransactionManager")
    public void write(String topicName, CreatePaymentRequestMessage createPaymentRequestMessage) {
        kafkaTemplate.send(topicName, createPaymentRequestMessage);
    }
}
