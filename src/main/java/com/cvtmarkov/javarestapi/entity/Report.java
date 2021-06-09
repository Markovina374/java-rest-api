package com.cvtmarkov.javarestapi.entity;

import com.cvtmarkov.javarestapi.entity.Cost;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
@Data
public class Report {
    private int day;
    private List<Cost> list;
    private BigDecimal maxOfDay;
    private HashMap<Integer,BigDecimal> maxOfCategory;
}
