package com.cvtmarkov.javarestapi.service;

import com.cvtmarkov.javarestapi.entity.Report;
import com.cvtmarkov.javarestapi.repository.CategoryRepository;
import com.cvtmarkov.javarestapi.repository.CostRepository;
import com.cvtmarkov.javarestapi.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Сервис для работы с Отчетами
 */
@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private CostRepository costRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Метод возвращает отчет по всем месяцам.
     * Он выводит список месяцев в которых были сделаны расходы
     * и сумму расходов за месяц
     * @return - отчет
     */
    public HashMap<String, BigDecimal> monthlyAmount() {
        HashMap<String, BigDecimal> maxValueForMonth = new HashMap<>();
        List<Integer> listMonth = reportRepository.findAllMonthsWhereExpensesWere();
        for (Integer list : listMonth) {
            maxValueForMonth.put("Sum for month " + list, reportRepository.sumOfMonth(list));
        }
        return maxValueForMonth;
    }

    /**
     * Метод возвращает объект Отчет по заданной дате
     * @param month - месяц
     * @param day - день
     * @return - отчет
     */
    public Report getReport(int month, int day) {
        List<Integer> idCategoryList = categoryRepository.findCategoryByDate(month, day);
        HashMap<Integer, BigDecimal> hashMap = new HashMap<>();
        for (int id : idCategoryList) {
            hashMap.put(id, categoryRepository.sumOfCategoryOfDate(id, month, day));
        }

        Report report = new Report();
        report.setDay(day);
        report.setList(costRepository.findCostFromDate(month, day));
        report.setSumOfCategory(hashMap);
        report.setSumOfDate(reportRepository.sumOfDay(month, day));

        return report;
    }

    /**
     * Метод возвращает детализированный отчет за месяц.
     * Он возвращает отчет по каждому дню в месяце
     * в которых были сделаны расходы 
     * с суммой расходов за день и суммой расходов по категориям
     * @param month - месяц
     * @return - детализированный отчет
     */
    public List<Report> monthlyDetailedReport(int month) {
        List<Integer> listDay = reportRepository.findAllDayWhereExpensesMonth(month);
        List<Report> reports = new ArrayList<>();
        for (int day : listDay
        ) {
            reports.add(getReport(month, day));
        }
        return reports;
    }

}
