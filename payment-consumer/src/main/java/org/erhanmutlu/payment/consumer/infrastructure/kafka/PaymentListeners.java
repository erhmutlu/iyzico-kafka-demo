package org.erhanmutlu.payment.consumer.infrastructure.kafka;

import org.erhanmutlu.payment.common.kafka.PaymentAuthRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.event.*;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.Date;

//@Service
public class PaymentListeners {

    private static final Logger logger = LoggerFactory.getLogger(PaymentListeners.class);

//    @KafkaListener(topics = "t_payment_request", groupId = "payment-group-1", containerFactory = "kafkaListenerContainerFactory", errorHandler = "kafkaListenerErrorHandler")
//    @SendTo("Topic2")
    public PaymentAuthRequestMessage consume(PaymentAuthRequestMessage message,
                                             @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("partition: {}, Message read. uniqueId: {}, conversationId: {}", partition, message.getUniqueId(), message.getConversationId());
        wait4();
        logger.info("done");
//        throw new RuntimeException("asdasd");
        return message;
    }

    private void wait4() {
        long start = new Date().getTime();
        while (new Date().getTime() - start < 3000L) {}
    }

//    @EventListener
    public void eventHandler(ListenerContainerIdleEvent event) {
        logger.info("listenerId: {}, topics: {}, idleFor: {}", event.getListenerId(), ((KafkaMessageListenerContainer)event.getSource()).getAssignedPartitions(), event.getIdleTime());
    }
}
