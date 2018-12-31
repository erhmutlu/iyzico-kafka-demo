package org.erhanmutlu.payment.consumer.infrastructure.kafka;

import org.erhanmutlu.kafkacommon.CreatePaymentRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class KafkaListeners {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListeners.class);

    @KafkaListener(topics = "TopicY", groupId = "payment-group-1", containerFactory = "kafkaListenerContainerFactory", errorHandler = "kafkaListenerErrorHandler")
    @SendTo("Topic2")
    public CreatePaymentRequestMessage consume(CreatePaymentRequestMessage message,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("partition: {}, Message read. uniqueId: {}, conversationId: {}, paymentType: {}", partition, message.getUniqueId(), message.getConversationId(), message.getPaymentType());
        wait4();
        logger.info("awake");
//        throw new RuntimeException("asdasd");
        return message;
    }

    private void wait4() {
        long start = new Date().getTime();
        while (new Date().getTime() - start < 3000L) {}
    }
}
