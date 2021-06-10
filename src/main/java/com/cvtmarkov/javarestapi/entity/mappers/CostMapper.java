package com.cvtmarkov.javarestapi.entity.mappers;

import com.cvtmarkov.javarestapi.entity.Cost;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Сопоставление объекта "Расход" с сущностью в базе данных
 */
public class CostMapper implements RowMapper<Cost> {
    @Override
    public Cost mapRow(ResultSet resultSet, int i) throws SQLException {
        Cost cost = new Cost();
        cost.setId(resultSet.getLong("id"));
        cost.setValue(resultSet.getBigDecimal("value"));
        cost.setDate(resultSet.getDate("date").toLocalDate());
        cost.setCategoryId(resultSet.getLong("category_id"));

        return cost;
    }
}
