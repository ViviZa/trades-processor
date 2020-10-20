package com.tradesprocessor.tradesprocessor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trade {

    @JsonProperty("ALL_TRADE_DB_ID")
    private long tradeID;

    @JsonProperty("PRICE")
    private double price;

}
