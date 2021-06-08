package com.cvtmarkov.javarestapi.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Cost {
    private long Id;
    private BigDecimal value;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private long categoryId;

}
