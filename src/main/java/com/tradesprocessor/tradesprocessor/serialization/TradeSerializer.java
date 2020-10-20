package com.tradesprocessor.tradesprocessor.serialization;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class TradeSerializer implements Serializer {

    @Override
    public byte[] serialize(final String s, final Object o) {
        byte[] retVal = null;
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(o).getBytes();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }
}
