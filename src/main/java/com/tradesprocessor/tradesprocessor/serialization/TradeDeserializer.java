package com.tradesprocessor.tradesprocessor.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradesprocessor.tradesprocessor.dto.Trade;
import org.apache.kafka.common.serialization.Deserializer;

public class TradeDeserializer implements Deserializer {

    @Override
    public Object deserialize(final String s, final byte[] bytes) {
        final ObjectMapper mapper = new ObjectMapper();
        Trade trade = null;
        try {
            trade = mapper.readValue(bytes, Trade.class);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return trade;
    }
}
