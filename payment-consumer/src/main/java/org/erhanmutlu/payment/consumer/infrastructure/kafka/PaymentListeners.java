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
public class PaymentListeners {

    private static final Logger logger = LoggerFactory.getLogger(PaymentListeners.class);

    @KafkaHandler
    public void consumePaymentAuthMessage(PaymentAuthRequestMessage message,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("consuming auth message from partition: {} with uniqueId: {}, conversationId: {}", partition, message.getUniqueId(), message.getConversationId());
        wait3();
        logger.info("done");
    }

    @KafkaHandler
    public void consumePaymentPreAuthMessage(PaymentPreAuthRequestMessage message,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("consuming pre-auth message from partition: {} with uniqueId: {}, conversationId: {}", partition, message.getUniqueId(), message.getConversationId());
        wait3();
        logger.info("done");
    }

    @KafkaHandler
    public void consumePaymentPostAuthMessage(PaymentPostAuthRequestMessage message,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("consuming post-auth message from partition: {} with uniqueId: {}", partition, message.getUniqueId());
        wait3();
        logger.info("done");
    }

    private void wait3() {
        try {
            Thread.sleep(3000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
