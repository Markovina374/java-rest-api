package com.cvtmarkov.javarestapi.entity.mappers;

import com.cvtmarkov.javarestapi.entity.MonthLimit;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MonthLimitMapper implements RowMapper<MonthLimit> {

    @Override
    public MonthLimit mapRow(ResultSet resultSet, int i) throws SQLException {
        MonthLimit monthLimit = new MonthLimit();
        monthLimit.setMonth(resultSet.getInt("numberMonth"));
        monthLimit.setSumOfMonth(resultSet.getBigDecimal("sumOfMonth"));
        monthLimit.setLimit(resultSet.getBigDecimal("limit"));
        return monthLimit;
    }
}