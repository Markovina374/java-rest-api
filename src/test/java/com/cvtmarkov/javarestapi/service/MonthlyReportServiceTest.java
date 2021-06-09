package com.cvtmarkov.javarestapi.service;

import com.cvtmarkov.javarestapi.entity.MonthLimit;
import com.cvtmarkov.javarestapi.repository.MonthLimitRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class MonthlyReportServiceTest {
    @Autowired
    private MonthlyReportService monthlyReportService;
    @Autowired
    private MonthLimitRepository monthLimitRepository;
    private MonthLimit testLimit = new MonthLimit();

    @Before


    @Test
    void monthlyAmount() {


    }

    @Test
    void getReport() {
    }
}