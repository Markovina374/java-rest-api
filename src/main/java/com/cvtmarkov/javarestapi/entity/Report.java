package com.cvtmarkov.javarestapi.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
/**
 * Объект "Отчет"
 */
@Data
public class Report {
    private int day;
    private List<Cost> list;
    private BigDecimal sumOfDate;
    private HashMap<Integer, BigDecimal> sumOfCategory;
}
