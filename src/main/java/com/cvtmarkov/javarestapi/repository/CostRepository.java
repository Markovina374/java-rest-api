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
        return jdbcTemplate.query("SELECT * FROM cost",
                new Object[]{}, new CostMapper());
    }

    @Override
    public Cost findById(long id) {
        return jdbcTemplate.query("SELECT * FROM cost WHERE id = ?",
                new Object[]{id}, new CostMapper())
                .stream()
                .findAny()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update("DELETE FROM cost WHERE id = ?", new Object[]{id}, new CostMapper());
    }

    @Override
    public Cost save(@Nullable Cost cost) {
        jdbcTemplate.update("INSERT INTO cost VALUES(?,?,?,?)",
                cost.getId(), cost.getValue(),
                cost.getDate(), cost.getCategoryId());
        return cost;
    }

    @Override
    public Cost update(long id, @Nullable Cost newCost) {
        newCost.setId(id);
        jdbcTemplate.update("UPDATE cost SET value = ?, date = ?, category_id = ? WHERE id = ?",
                newCost.getValue(), newCost.getDate(),
                newCost.getCategoryId(), id);
        return newCost;
    }




}
