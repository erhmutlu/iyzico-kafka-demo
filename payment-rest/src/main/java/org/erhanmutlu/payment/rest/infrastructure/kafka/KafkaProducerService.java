package org.erhanmutlu.payment.rest.infrastructure.kafka;

import org.erhanmutlu.kafkacommon.IyzicoIdempotentMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private KafkaTemplate<String, IyzicoIdempotentMessage> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, IyzicoIdempotentMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional("kafkaTransactionManager")
    public void write(String topicName, IyzicoIdempotentMessage iyzicoIdempotentMessage) {
        logger.info("message with uniqueId: {} will be written into topic: {}", iyzicoIdempotentMessage.getUniqueId(), topicName);
        kafkaTemplate.send(topicName, iyzicoIdempotentMessage);
    }
}
