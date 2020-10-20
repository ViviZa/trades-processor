package com.tradesprocessor.tradesprocessor;

import com.tradesprocessor.tradesprocessor.dto.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TradeProducerService {

    @Autowired
    private KafkaTemplate<String, Trade> kafkaTemplate;

    public void sendMessage(final Trade trade) {
        final Message<Trade> message = MessageBuilder.withPayload(trade).setHeader(KafkaHeaders.TOPIC, "test-topic").setHeader(KafkaHeaders.PARTITION_ID, 1)
                .build();
        log.info("Sending trade");
        this.kafkaTemplate.send(message);
    }
}
