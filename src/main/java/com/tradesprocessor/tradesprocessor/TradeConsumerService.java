package com.tradesprocessor.tradesprocessor;

import com.tradesprocessor.tradesprocessor.dto.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TradeConsumerService {

    @KafkaListener(
            topicPartitions = { @TopicPartition(topic = "test-topic", partitions = { "1" })},
            groupId = "group_one",
            containerFactory = "kafkaListenerContainerFactory",
            autoStartup = "true")
    public void consumeFromCoreTopicPartitionZERO(@Payload final List<Trade> consumedMessages){
        log.info("Consumed " + consumedMessages.size());
    }
}
