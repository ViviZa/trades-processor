package com.tradesprocessor.tradesprocessor;

import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class KafkaProducerIT extends IntegrationTestConfigWithPortAndTestProfile {

    @Autowired
    private TradeClient tradeClient;

    @Test
    public void sendTradesToKafka() {
        // given
        initTestQueueReceiverForTopic("test-topic");

        // when
        tradeClient.createTradeData();

        //then
        Awaitility.await()
                .atMost(Duration.ofSeconds(10)).pollDelay(Duration.ofMillis(100)).untilAsserted(() -> {
                    assertThat(records).hasSize(6);
                }
        );
    }
}
