package com.tradesprocessor.tradesprocessor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeStatistics {
    private double averagePrice = 0.0;
    private int countTrades = 0;
    private double sumPrice = 0.0;

    public TradeStatistics add(final Trade trade) {
        this.countTrades = this.countTrades + 1;
        this.sumPrice = this.sumPrice + trade.getPrice();
        return this;
    }

    public TradeStatistics computeAveragePrice() {
        this.averagePrice = this.sumPrice / this.countTrades;
        return this;
    }

}
