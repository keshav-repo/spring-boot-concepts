package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StockValue {
    private String symbol;
    private String currency;
    private LocalDateTime date;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;
}
