package org.erhanmutlu.payment.consumer.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.erhanmutlu.payment.consumer.infrastructure.kafka.CustomConsumerAwareRebalanceListener;
import org.erhanmutlu.payment.consumer.infrastructure.kafka.CustomErrorHandler;
import org.erhanmutlu.payment.consumer.model.CreatePaymentRequestMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.LogIfLevelEnabled;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ThreadPoolTaskExecutor messageProcessorExecutor() {
        ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
        exec.setCorePoolSize(20);
        exec.setMaxPoolSize(45);
        exec.setKeepAliveSeconds(60);
        exec.setThreadNamePrefix("kafkaConsumer-");
        return exec;
    }

    @Bean
    public ConsumerAwareRebalanceListener consumerAwareRebalanceListener() {
        return new CustomConsumerAwareRebalanceListener();
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, CreatePaymentRequestMessage>> kafkaListenerContainerFactory(KafkaTemplate kafkaTemplate) {

        ConcurrentKafkaListenerContainerFactory<String, CreatePaymentRequestMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(8);
        factory.setAutoStartup(true);
        factory.setReplyTemplate(kafkaTemplate);
//        factory.getContainerProperties().setPollTimeout(6000);
        factory.getContainerProperties().setConsumerTaskExecutor(messageProcessorExecutor());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        factory.getContainerProperties().setCommitLogLevel(LogIfLevelEnabled.Level.INFO);

        return factory;
    }

    @Bean
    public ConsumerAwareListenerErrorHandler kafkaListenerErrorHandler() {
        return new CustomErrorHandler();
    }

    @Bean
    public ConsumerFactory<String, CreatePaymentRequestMessage> consumerFactory() {
        DefaultKafkaConsumerFactory<String, CreatePaymentRequestMessage> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerConfigs());

//        CreatePaymentRequestMessageDeserializer valueDeserializer = new CreatePaymentRequestMessageDeserializer();
//        valueDeserializer.setRemoveTypeHeaders(true);
        JsonDeserializer<CreatePaymentRequestMessage> valueDeserializer = new JsonDeserializer<>(CreatePaymentRequestMessage.class, new ObjectMapper());
        consumerFactory.setValueDeserializer(valueDeserializer);

        consumerFactory.setKeyDeserializer(new StringDeserializer());

        return consumerFactory;
    }

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-group-1");
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "4000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "50000");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());
//        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
//        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "10000");

        return props;
    }
}
