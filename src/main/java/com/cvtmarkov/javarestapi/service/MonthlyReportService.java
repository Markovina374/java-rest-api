package com.cvtmarkov.javarestapi.service;

import com.cvtmarkov.javarestapi.entity.Report;
import com.cvtmarkov.javarestapi.repository.CategoryRepository;
import com.cvtmarkov.javarestapi.repository.CostRepository;
import com.cvtmarkov.javarestapi.repository.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MonthlyReportService {
    @Autowired
    private DateRepository dateRepository;
    @Autowired
    private CostRepository costRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public HashMap<String, BigDecimal> monthlyAmount() {


        HashMap<String, BigDecimal> maxValueForMonth = new HashMap<>();
        List<Integer> listMonth = dateRepository.findAllMonthsWhereExpensesWere();
        for (Integer list : listMonth) {
            maxValueForMonth.put("Sum for month " + list, dateRepository.sumOfMonth(list));
        }
        return maxValueForMonth;
    }

    public Report getReport(int month, int day) {
        List<Integer> idCategoryList = categoryRepository.findCategoryByDate(month, day);
        HashMap<Integer, BigDecimal> hashMap = new HashMap<>();
        for (int id : idCategoryList) {
            hashMap.put(id, categoryRepository.sumOfCategoryOfDate(id, month, day));
        }

        Report report = new Report();
        report.setDay(day);
        report.setList(costRepository.findCostFromDate(month, day));
        report.setMaxOfCategory(hashMap);
        report.setMaxOfDay(dateRepository.maxSumOfDay(month, day));

        return report;
    }


    public List<Report> mounthDetalizedReport(int month) {
        List<Integer> listDay = dateRepository.findAllDayWhereExpensesMonth(month);
        List<Report> reports = new ArrayList<>();
        for (int day : listDay
        ) {
            reports.add(getReport(month, day));
        }
        return reports;
    }

}
