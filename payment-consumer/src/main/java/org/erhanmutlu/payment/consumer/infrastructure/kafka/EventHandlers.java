package org.erhanmutlu.payment.consumer.infrastructure.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class EventHandlers {

    private static final Logger logger = LoggerFactory.getLogger(EventHandlers.class);

    @EventListener
    public void eventHandler(ListenerContainerIdleEvent event) {
        logger.info("listenerId: {}, topics: {}, idleFor: {}", event.getListenerId(), ((KafkaMessageListenerContainer) event.getSource()).getAssignedPartitions(), event.getIdleTime());
    }
}
