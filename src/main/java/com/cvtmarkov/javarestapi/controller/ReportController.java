package com.cvtmarkov.javarestapi.controller;

import com.cvtmarkov.javarestapi.entity.Report;
import com.cvtmarkov.javarestapi.repository.ReportRepository;
import com.cvtmarkov.javarestapi.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


/**
 REST контроллер для составления детального отчёта.
 принимает в себя:
 @see ReportService - сервис в котором производится работа над детальным отчетом
 */
@RestController
@RequestMapping("rest/costs/month")
public class ReportController {
    @Autowired
    private final ReportService reportService;


    public ReportController(ReportService reportService) {
        this.reportService = reportService;

    }

    /**
     * Возвращает общий отчет за все месяцы, показывая месяц и общую сумму расходов
     * @return - общий отчет
     */
    @GetMapping
    public HashMap<String, BigDecimal> findSumCostsForMonth() {
        return reportService.monthlyAmount();
    }

    /**
     * Предоставляет детальный отчет за месяц
     * @param month - месяц который нас интересует
     * @return - детальный отчет за месяц
     */
    @GetMapping("detailedReport{month}")
    public List<Report> monthlyReport(@PathVariable("month") int month) {
        return reportService.mounthDetalizedReport(month);
    }




}
