package org.erhanmutlu.kafkalistener;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;

import java.util.Collection;

public class CustomConsumerAwareRebalanceListener implements ConsumerAwareRebalanceListener{

    private static final Logger logger = LoggerFactory.getLogger(CustomConsumerAwareRebalanceListener.class);

    @Override
    public void onPartitionsRevokedBeforeCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        logger.info("onPartitionsRevokedBeforeCommit");
        ConsumerAwareRebalanceListener.super.onPartitionsRevokedBeforeCommit(consumer, partitions);
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        logger.info("onPartitionsAssigned");
        ConsumerAwareRebalanceListener.super.onPartitionsAssigned(partitions);
    }
}
