package com.tradesprocessor.tradesprocessor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trade {

    @JsonProperty("trade_id")
    private long tradeID;

    @JsonProperty("price")
    private double price;

}
