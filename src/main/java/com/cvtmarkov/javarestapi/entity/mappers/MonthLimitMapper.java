package com.cvtmarkov.javarestapi.entity.mappers;

import com.cvtmarkov.javarestapi.entity.MonthLimit;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Сопоставление объекта "Месячный лимит" с сущностью в базе данных
 */
public class MonthLimitMapper implements RowMapper<MonthLimit> {

    @Override
    public MonthLimit mapRow(ResultSet resultSet, int i) throws SQLException {
        MonthLimit monthLimit = new MonthLimit();
        monthLimit.setMonth(resultSet.getInt("numberMonth"));
        monthLimit.setSumOfMonth(resultSet.getBigDecimal("sum_of_month"));
        monthLimit.setLimit(resultSet.getBigDecimal("limit_of_month"));
        return monthLimit;
    }
}
