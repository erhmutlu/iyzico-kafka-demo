package org.erhanmutlu.payment.rest;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;

public class CustomKafkaProducerListener<T, R> implements ProducerListener<T, R> {

    private static final Logger logger = LoggerFactory.getLogger(CustomKafkaProducerListener.class);

    @Override
    public void onSuccess(String topic, Integer partition, T key, R value, RecordMetadata recordMetadata) {
        logger.info("success for writing topic: {}", topic);

    }

    @Override
    public void onError(String topic, Integer partition, T key, R value, Exception exception) {
        logger.info("failure for writing topic: {}", topic);
    }
}
