package org.erhanmutlu.payment.rest.infrastructure.kafka;

import org.erhanmutlu.avro.CreatePaymentRequestMessage;
import org.erhanmutlu.payment.common.IyzicoIdempotentMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageProducerService {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducerService.class);

    private KafkaTemplate<String, CreatePaymentRequestMessage> kafkaTemplate;

    public MessageProducerService(KafkaTemplate<String, CreatePaymentRequestMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional("kafkaTransactionManager")
    public void write(String topicName, CreatePaymentRequestMessage iyzicoIdempotentMessage) {
        logger.info("message with uniqueId: {} will be written into topic: {}", iyzicoIdempotentMessage.getUniqueId(), topicName);
        kafkaTemplate.send(topicName, iyzicoIdempotentMessage);
    }
}
