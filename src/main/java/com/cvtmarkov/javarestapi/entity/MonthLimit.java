package com.cvtmarkov.javarestapi.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MonthLimit {
    private int month;
    private BigDecimal sumOfMonth;
    private BigDecimal limit;

}
