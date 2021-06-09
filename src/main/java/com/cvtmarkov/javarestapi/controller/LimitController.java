package com.cvtmarkov.javarestapi.controller;


import com.cvtmarkov.javarestapi.entity.MonthLimit;
import com.cvtmarkov.javarestapi.repository.MonthLimitRepository;
import com.cvtmarkov.javarestapi.service.MonthLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("rest/monthLimit")
public class LimitController {
    @Autowired
    private final MonthLimitService monthLimitService;
    @Autowired
    private final MonthLimitRepository monthLimitRepository;

    public LimitController(MonthLimitService monthLimitService, MonthLimitRepository monthLimitRepository) {
        this.monthLimitService = monthLimitService;
        this.monthLimitRepository = monthLimitRepository;
    }

    @GetMapping
    public List<MonthLimit> allCosts() {
        return monthLimitRepository.findAll();
    }


    @GetMapping("{month}")
    public MonthLimit getOne(@PathVariable("month") long month) {
        return monthLimitRepository.findById(month);
    }


    @PostMapping
    public MonthLimit create(@RequestBody MonthLimit cost) {
        return monthLimitRepository.save(cost);
    }

    @PutMapping("{month}")
    public MonthLimit update(@PathVariable("month") long month,
                             @RequestBody MonthLimit monthLimit) {
        return monthLimitRepository.update(month, monthLimit);
    }

    @DeleteMapping("{month}")
    public String delete(@PathVariable("month") long month) {
        return monthLimitRepository.deleteById(month);
    }


    @GetMapping("/checkLimit{month}")
    public String checkLimitForMonth(@PathVariable("month") long month) {
        return monthLimitService.limitMethod(month);
    }
    @GetMapping("/checkLimit")
    public List<HashMap<String, String>> checkLimitAllMonth(){
        return monthLimitService.checkAllMonthForLimit();
    }


}
