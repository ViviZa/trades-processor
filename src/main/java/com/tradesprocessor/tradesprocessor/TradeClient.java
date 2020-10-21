package com.tradesprocessor.tradesprocessor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradesprocessor.tradesprocessor.dto.Trade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class TradeClient {

    private List<Trade> tradeList;

    @Autowired
    private TradeProducerService tradeProducerService;

    @PostConstruct
    public void createTradeData(){
        this.tradeList = Arrays.asList(getTradeArrayFromFile());

        log.info("Start iterating through tradeList");
        for (int i = 0; i < this.tradeList.size(); i++) {
            if (i == 2 || i == 4) {
                waitSeconds(9000);
            }
            this.tradeProducerService.sendMessage(this.tradeList.get(i));
        }
    }

    private Trade[] getTradeArrayFromFile() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);

        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("all-trades.json");
        try {
            final String jsonString = IOUtils.toString(inputStream);
            final Trade[] tradeArray = objectMapper.readValue(jsonString, Trade[].class);

            return tradeArray;

        } catch (final IOException e) {
            log.info("Could not parse inputstream", e);
        }

        return new Trade[0];
    }

    private void waitSeconds(final int milliseconds) {
        log.info("start waiting for {} seconds", milliseconds);
        try {
            Thread.sleep(milliseconds);
        } catch (final InterruptedException e) {
            log.error("Could not wait", e);
        }
        log.info("finished waiting ...");
    }
}
