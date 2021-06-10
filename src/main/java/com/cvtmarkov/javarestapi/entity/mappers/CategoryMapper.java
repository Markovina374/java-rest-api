package com.cvtmarkov.javarestapi.entity.mappers;

import com.cvtmarkov.javarestapi.entity.Category;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Сопоставление объекта "Категория" с сущностью в базе данных
 */
@Data
public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet resultSet, int i) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setName(resultSet.getString("name"));
        return category;
    }
}