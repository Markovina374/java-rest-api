package com.cvtmarkov.javarestapi.controller;


import com.cvtmarkov.javarestapi.service.MonthLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("rest/monthLimit")
public class LimitController {
    @Autowired
    private MonthLimitService monthLimit;
    @GetMapping
    public BigDecimal mountLimit(){
        return monthLimit.getLimit();
    }
}
