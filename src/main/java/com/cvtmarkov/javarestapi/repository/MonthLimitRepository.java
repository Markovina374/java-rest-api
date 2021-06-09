package com.cvtmarkov.javarestapi.repository;

import com.cvtmarkov.javarestapi.entity.Cost;
import com.cvtmarkov.javarestapi.entity.MonthLimit;
import com.cvtmarkov.javarestapi.entity.mappers.CostMapper;
import com.cvtmarkov.javarestapi.entity.mappers.MonthLimitMapper;
import com.cvtmarkov.javarestapi.exception.NotFoundException;
import com.cvtmarkov.javarestapi.service.MonthLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Nullable;
import java.util.List;

public class MonthLimitRepository implements CrudRepository<MonthLimit> {
    @Autowired
    private MonthLimitService monthLimitService;
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public MonthLimitRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MonthLimit> findAll() {
        return jdbcTemplate.query("SELECT * FROM month_limit",
                new Object[]{}, new MonthLimitMapper());
    }

    @Override
    public MonthLimit findById(long month) {
        return jdbcTemplate.query("SELECT * FROM month_limit WHERE numberMonth = ?",
                new Object[]{month}, new MonthLimitMapper())
                .stream()
                .findAny()
                .orElseThrow(NotFoundException::new);

    }

    @Override
    public String deleteById(long month) {
        if(jdbcTemplate.update("DELETE FROM month_limit WHERE numberMonth = ?", month) == 1){
            return "MonthLimit with this number = "+month+" deleted!";
        }else {
            return "MonthLimit with this number = "+month+" was not found";
        }
    }

    @Override
    public MonthLimit save(MonthLimit limit) {
        jdbcTemplate.update("INSERT INTO month_limit VALUES(?,?,?)",
                limit.getMonth(), limit.getSumOfMonth(), monthLimitService.getLimit());
        return limit;
    }

    @Override
    public MonthLimit update(long month, @Nullable MonthLimit limit) {
            limit.setMonth((int) month);
            jdbcTemplate.update("UPDATE month_limit SET sumOfMonth = ?, limit = ?, WHERE numberMonth = ?",
                    limit.getSumOfMonth(), limit.getLimit(), monthLimitService.getLimit());
            return limit;
        }
    }

