package com.cvtmarkov.javarestapi.service;

import com.cvtmarkov.javarestapi.entity.Category;
import com.cvtmarkov.javarestapi.entity.Cost;
import com.cvtmarkov.javarestapi.entity.Report;
import com.cvtmarkov.javarestapi.repository.CategoryRepository;
import com.cvtmarkov.javarestapi.repository.CostRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReportServiceTest {

    @Autowired
    private CostRepository costRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ReportService reportService;


    @Test
    void monthlyAmount() {
        Assert.assertNotNull(reportService.monthlyAmount());


    }

    @Before
    void createTestObject() {
        Category category = new Category();
        category.setId(22);
        category.setName("Test");

        Cost cost = new Cost();
        cost.setCategoryId(22);
        cost.setValue(new BigDecimal(333.33));
        cost.setId(222);
        cost.setDate(LocalDate.now());
        categoryRepository.save(category);
        costRepository.save(cost);
    }

    @Test
    void getReport() {
        createTestObject();
        deleteTestObject();
        Assert.assertEquals(Report.class, reportService.getReport(6, 10).getClass());

    }

    @After
    void deleteTestObject() {
        categoryRepository.deleteById(22);
    }
}