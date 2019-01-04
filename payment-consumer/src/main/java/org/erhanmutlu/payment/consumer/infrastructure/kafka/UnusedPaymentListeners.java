package org.erhanmutlu.payment.consumer.infrastructure.kafka;

import org.erhanmutlu.payment.common.kafka.IyzicoIdempotentMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UnusedPaymentListeners {

    private static final Logger logger = LoggerFactory.getLogger(UnusedPaymentListeners.class);

    @KafkaListener(topics = "t_payment_request", groupId = "payment-group-1", containerFactory = "kafkaListenerContainerFactory", errorHandler = "kafkaListenerErrorHandler")
    @SendTo("Topic2")
    public String consume(IyzicoIdempotentMessage message,
                                             @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("consuming by dummy listener from partition: {} with uniqueId: {}", partition, message.getUniqueId());
        wait3();
        logger.info("done");
//        throw new RuntimeException("asdasd");
        return message.getUniqueId();
    }

    private void wait3() {
        try {
            Thread.sleep(3000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
