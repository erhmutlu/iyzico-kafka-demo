package org.erhanmutlu.kafkalistener;

import org.erhanmutlu.kafkacommon.CreatePaymentRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@KafkaListener(topics = "TopicY", groupId = "payment-group-2", containerFactory = "kafkaListenerContainerFactory")
public class KafkaListeners2 {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListeners2.class);

    @KafkaHandler(isDefault = true)
    public void consume(CreatePaymentRequestMessage message,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("partition: {}, Message read. uniqueId: {}, conversationId: {}, paymentType: {}", partition, message.getUniqueId(), message.getConversationId(), message.getPaymentType());
        wait4();
        logger.info("awake");
    }

    private void wait4() {
        long start = new Date().getTime();
        while (new Date().getTime() - start < 3000L) {}
    }
}
