package com.cvtmarkov.javarestapi.controller;

import com.cvtmarkov.javarestapi.entity.Report;
import com.cvtmarkov.javarestapi.repository.DateRepository;
import com.cvtmarkov.javarestapi.service.MonthlyReportService;
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
public class DateController {
    @Autowired
    private final MonthlyReportService monthService;
    @Autowired
    private final DateRepository dateRepository;

    public DateController(MonthlyReportService monthService, DateRepository dateRepository) {
        this.monthService = monthService;
        this.dateRepository = dateRepository;
    }

    @GetMapping
    public HashMap<String, BigDecimal> findCostsForMonth() {
        return monthService.monthlyAmount();
    }

    @GetMapping("listMonth")
    public List<Integer> listMonth() {
        return dateRepository.findAllMonthsWhereExpensesWere();
    }

    @GetMapping("{month}")
    public List<Integer> listDay(@PathVariable("month") int month) {
        return dateRepository.findAllDayWhereExpensesMonth(month);
    }

    @GetMapping("sum{month}/day{day}")
    public BigDecimal listSumOfDay(@PathVariable("month") int month, @PathVariable("day") int day) {
        return dateRepository.maxSumOfDay(month, day);
    }

    @GetMapping("monthlyReport{month}")
    public List<Report> monthlyReport(@PathVariable("month") int month) {
        return monthService.mounthDetalizedReport(month);
    }

    @GetMapping("sum{month}")
    public BigDecimal maxLimitMonth(@PathVariable("month") int month) {
        return dateRepository.sumOfMonth(month);
    }


}
