package org.erhanmutlu.payment.consumer.infrastructure.kafka;

import org.erhanmutlu.payment.common.kafka.PaymentAuthRequestMessage;
import org.erhanmutlu.payment.common.kafka.PaymentPostAuthRequestMessage;
import org.erhanmutlu.payment.common.kafka.PaymentPreAuthRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@KafkaListener(topics = "t_payment_request", groupId = "payment-group-2", containerFactory = "kafkaListenerContainerFactory")
public class KafkaListeners2 {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListeners2.class);

    @KafkaHandler
    public void consumePaymentAuthMessage(PaymentAuthRequestMessage message,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("consuming auth message from partition: {} with uniqueId: {}, conversationId: {}", partition, message.getUniqueId(), message.getConversationId());
        wait4();
        logger.info("done");
    }

    @KafkaHandler
    public void consumePaymentPreAuthMessage(PaymentPreAuthRequestMessage message,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("consuming pre-auth message from partition: {} with uniqueId: {}, conversationId: {}", partition, message.getUniqueId(), message.getConversationId());
        wait4();
        logger.info("done");
    }

    @KafkaHandler
    public void consumePaymentPostAuthMessage(PaymentPostAuthRequestMessage message,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("consuming post-auth message from partition: {} with uniqueId: {}", partition, message.getUniqueId());
        wait4();
        logger.info("done");
    }

    private void wait4() {
        long start = new Date().getTime();
        while (new Date().getTime() - start < 3000L) {}
    }
}
