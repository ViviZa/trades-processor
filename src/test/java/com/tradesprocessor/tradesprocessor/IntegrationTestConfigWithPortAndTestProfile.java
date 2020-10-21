package com.tradesprocessor.tradesprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TradesProcessorApplication.class)
public class IntegrationTestConfigWithPortAndTestProfile {
    @Autowired
    protected ObjectMapper objectMapper;
    protected List<String> bootstrapServers = List.of("localhost:9092");
    private KafkaMessageListenerContainer<String, String> listenerContainer;
    protected BlockingQueue<ConsumerRecord<String, String>> records = new LinkedBlockingQueue<>();

    @AfterEach
    public void stopListenerContainer() {
        if (listenerContainer != null) {
            listenerContainer.stop();
        }
    }

    protected void initTestQueueReceiverForTopic(String topic) {
        initTestQueueReceiverForTopicInternally(topic, bootstrapServers);
    }

    private void initTestQueueReceiverForTopicInternally(String topic, List<String> kafkaBrokers) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "json");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<String, String>(props);
        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setMissingTopicsFatal(false);
        listenerContainer = new KafkaMessageListenerContainer<>(cf, containerProps);

        listenerContainer.setupMessageListener((MessageListener<String, String>) records::add);
        listenerContainer.setBeanName("beanForTopic-" + topic);
        listenerContainer.start();
    }
}