package com.cvtmarkov.javarestapi.controller;

import com.cvtmarkov.javarestapi.entity.Cost;
import com.cvtmarkov.javarestapi.repository.MonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("rest/costs/month")
public class MonthController {
    @Autowired
    MonthRepository monthRepository;

    @GetMapping("{month}")
    public HashMap<HashMap<String, BigDecimal>, List<Cost>> findCostsForMonth(@PathVariable("month") int month){
        return monthRepository.monthlyAmount(month);
    }

}
