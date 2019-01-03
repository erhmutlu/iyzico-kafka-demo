package org.erhanmutlu.payment.rest.infrastructure.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.erhanmutlu.payment.common.kafka.IyzicoIdempotentMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;

public class MessageProducerListener implements ProducerListener<String, IyzicoIdempotentMessage> {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducerListener.class);

    @Override
    public void onSuccess(String topic, Integer partition, String key, IyzicoIdempotentMessage message, RecordMetadata recordMetadata) {
        logger.info("success for writing topic: {} for uniqueId: {}", topic, message.getUniqueId());

    }

    @Override
    public void onError(String topic, Integer partition, String key, IyzicoIdempotentMessage message, Exception exception) {
        logger.info("failure for writing topic: {} for uniqueId: {}", topic, message.getUniqueId());
    }
}
