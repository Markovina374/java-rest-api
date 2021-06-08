package com.cvtmarkov.javarestapi.repository;

import com.cvtmarkov.javarestapi.entity.Cost;
import com.cvtmarkov.javarestapi.entity.mappers.CategoryMapper;
import com.cvtmarkov.javarestapi.entity.mappers.CostMapper;
import com.cvtmarkov.javarestapi.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Repository
public class CostRepository implements CrudRepository<Cost>{

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public CostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Cost> findAll() {
        return jdbcTemplate.query("SELECT * FROM cost", new Object[]{}, new CostMapper());
    }

    @Override
    public Cost findById(long id) {
        return jdbcTemplate.query("SELECT * FROM cost WHERE id = ?", new Object[]{id}, new CostMapper())
                .stream()
                .findAny()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update("DELETE FROM cost WHERE id = ?", new Object[]{id}, new CategoryMapper());
    }

    @Override
    public Cost save(@Nullable Cost cost) {
        jdbcTemplate.update("INSERT INTO cost VALUES(?,?,?,?)", cost.getId(), cost.getValue(), cost.getDate(), cost.getCategoryId());
        return cost;
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
        maximumHashmap.put("Maximum for month:", maxSumOfMonth(month));
        hashMap.put(maximumHashmap,findByMount(month));
        return hashMap;
    }
}
