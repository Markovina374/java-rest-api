package com.cvtmarkov.javarestapi.repository;

import com.cvtmarkov.javarestapi.entity.Cost;
import com.cvtmarkov.javarestapi.entity.mappers.CostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class DateRepository {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public DateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Cost> findByMount(int month) {
        return jdbcTemplate.query("SELECT * FROM cost WHERE  MONTH(date) = ?", new Object[]{month}, new CostMapper());
    }

    public List<Integer> findAllDayWhereExpensesMonth(int month) {
        return jdbcTemplate.queryForList("SELECT DISTINCT DAY(date) FROM cost WHERE MONTH(date) = ?", new Object[]{month}, Integer.class);
    }

    public BigDecimal maxSumOfDay(int month, int day) {
        return jdbcTemplate.queryForObject("SELECT SUM(value) FROM cost WHERE  DAY(date) = ? AND MONTH(date) = ? ", new Object[]{day, month}, BigDecimal.class);
    }

    public BigDecimal sumOfMonth(int month) {
        return jdbcTemplate.queryForObject("SELECT SUM(value) FROM cost WHERE  MONTH(date) = ?", new Object[]{month}, BigDecimal.class);
    }

    public List<Integer> findAllMonthsWhereExpensesWere() {
        return jdbcTemplate.queryForList("SELECT DISTINCT month(date) From cost", Integer.class);
    }


}
