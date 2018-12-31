package org.erhanmutlu.payment.rest;

import org.erhanmutlu.kafkacommon.CreatePaymentRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class CustomKafkaWriter {

    private static final Logger logger = LoggerFactory.getLogger(CustomKafkaWriter.class);

    private KafkaTemplate<String, Object> kafkaTemplate;

    public CustomKafkaWriter(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional("kafkaTransactionManager")
    public void write(String topicName, CreatePaymentRequestMessage createPaymentRequestMessage) {
//        kafkaTemplate.send(topicName, createPaymentRequestMessage);
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, createPaymentRequestMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                logger.info("success");
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info("fail");
            }
        });
    }

//    @Transactional("kafkaTransactionManager")
//    public void write(List<String> topicNames, Payment payment){
//        kafkaTemplate.executeInTransaction(kafkaOperations -> {
//            topicNames.forEach(topic -> kafkaOperations.send(topic, payment));
//            return true;
//        });
//    }
}
