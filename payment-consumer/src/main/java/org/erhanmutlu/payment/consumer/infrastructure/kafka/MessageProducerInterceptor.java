package org.erhanmutlu.payment.consumer.infrastructure.kafka;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class MessageProducerInterceptor implements ProducerInterceptor{

    private static final Logger logger = LoggerFactory.getLogger(MessageProducerInterceptor.class);

    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("onSend topic=%s key=%s value=%s %d \n",
                    record.topic(), record.key(), record.value().toString(),
                    record.partition()
            ));
        }
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception e) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("onAck topic=%s, partition=%d, offset=%d\n",
                    metadata.topic(), metadata.partition(), metadata.offset()
            ));
        }
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> map) {
    }
}
