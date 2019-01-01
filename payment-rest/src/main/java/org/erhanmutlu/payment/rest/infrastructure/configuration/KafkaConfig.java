package org.erhanmutlu.payment.rest.infrastructure.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.erhanmutlu.payment.common.IyzicoIdempotentMessage;
import org.erhanmutlu.payment.rest.infrastructure.kafka.MessageProducerListener;
import org.erhanmutlu.payment.rest.infrastructure.kafka.MessageProducerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean("kafkaTransactionManager")
    public KafkaTransactionManager kafkaTransactionManager() {
        KafkaTransactionManager ktm = new KafkaTransactionManager(producerFactory());
        ktm.setTransactionSynchronization(AbstractPlatformTransactionManager.SYNCHRONIZATION_NEVER);
        ktm.setRollbackOnCommitFailure(true);
        return ktm;
    }

    @Bean
    public ProducerFactory<String, IyzicoIdempotentMessage> producerFactory() {
        DefaultKafkaProducerFactory<String, IyzicoIdempotentMessage> producerFactory = new DefaultKafkaProducerFactory<>(producerConfigs());
        producerFactory.setTransactionIdPrefix("payment.publish."); //makes producer is idempotent
        return producerFactory;
    }

    @Bean
    public ProducerListener<String, IyzicoIdempotentMessage> producerListener() {
        return new MessageProducerListener();
    }

    @Bean
    public KafkaTemplate<String, IyzicoIdempotentMessage> kafkaTemplate() {
        KafkaTemplate<String, IyzicoIdempotentMessage> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        kafkaTemplate.setProducerListener(producerListener());
        return kafkaTemplate;
    }

    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "payment.publish.");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, MessageProducerInterceptor.class.getName());
        props.put(ProducerConfig.RETRIES_CONFIG, 10);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15000);
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);
//        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 10);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 16 * 1024 * 1024);  //default 32 MB
        return props;
    }
}