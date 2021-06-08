package com.cvtmarkov.javarestapi.entity;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Report {
    private int day;
    private BigDecimal maxValueOfDay;
    private long id_category;
    private BigDecimal maxValueOfCategory;
}
