package com.cvtmarkov.javarestapi.repository;

import com.cvtmarkov.javarestapi.entity.Cost;
import com.cvtmarkov.javarestapi.entity.mappers.CostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public class MonthRepository {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public MonthRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Cost> findByMount(int month){
        return jdbcTemplate.query("SELECT * FROM cost WHERE  MONTH(date) = ?", new Object[]{month}, new CostMapper());
    }
    public BigDecimal maxSumOfMonth(int month){
        return jdbcTemplate.queryForObject("SELECT SUM(value) FROM cost WHERE  MONTH(date) = ?", new Object[]{month}, BigDecimal.class);
    }

    public HashMap<HashMap<String, BigDecimal>,List<Cost>> monthlyAmount (int month){
        HashMap<HashMap<String, BigDecimal>, List<Cost>> hashMap = new HashMap<>();
        HashMap<String, BigDecimal> maximumHashmap = new HashMap<>();
        maximumHashmap.put("Sum for month:", maxSumOfMonth(month));
        hashMap.put(maximumHashmap,findByMount(month));
        return hashMap;
    }
}
