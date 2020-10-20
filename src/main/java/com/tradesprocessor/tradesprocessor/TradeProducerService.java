package com.tradesprocessor.tradesprocessor;

import com.tradesprocessor.tradesprocessor.dto.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class TradeProducerService {

    @Autowired
    private KafkaTemplate<String, Trade> kafkaTemplate;

    public void sendMessage(final Trade trade) {
        try {
            this.kafkaTemplate.send("test-topic", String.valueOf(trade.getTradeID()), trade).get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.warn("Error sending trade!", e);
        }
    }
}
