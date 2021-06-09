package com.cvtmarkov.javarestapi.service;

import com.cvtmarkov.javarestapi.repository.MonthLimitRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
class MonthLimitServiceTest {
    @Autowired
    private MonthLimitService monthLimitService;
    @Value("${limit}")
    BigDecimal limitForTest;
    @MockBean
    private MonthLimitRepository repository;

    @Test
    void getLimitFromProperty() {
        Assertions.assertEquals(limitForTest.abs(),monthLimitService.getLimitFromProperty());
    }

    @Test
    void limitMethod() {

        monthLimitService.limitMethod(9);
    }
}