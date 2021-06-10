package com.cvtmarkov.javarestapi.service;

import com.cvtmarkov.javarestapi.entity.MonthLimit;
import com.cvtmarkov.javarestapi.repository.MonthLimitRepository;
import org.junit.After;
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

    @Autowired
    private MonthLimitRepository monthLimitRepository;

    @Value("${limit}")
    BigDecimal limitForTest;


    @Test
    void getLimitFromProperty() {
        Assertions.assertEquals(limitForTest.abs(),monthLimitService.getLimitFromProperty());
    }

    @Before
    public void newTestLimit() {
        MonthLimit monthLimit = new MonthLimit();
        monthLimit.setLimit(new BigDecimal(4444.22));
        monthLimit.setMonth(13);
        monthLimit.setSumOfMonth(new BigDecimal(2222.44));
        monthLimitRepository.save(monthLimit);
    }
    @Test
    void limitMethod() {
        newTestLimit();

        Assert.assertEquals("Your limit value does not exceed the monthly amount " +
                "or an incorrect method was selected for processing the limit", monthLimitService.limitMethod(13));
        removeTestLimit();
    }
    @After
    public void removeTestLimit(){
        monthLimitRepository.deleteById(13);
    }
}