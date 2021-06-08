package com.cvtmarkov.javarestapi.repository;

import com.cvtmarkov.javarestapi.entity.Category;
import com.cvtmarkov.javarestapi.entity.mappers.CategoryMapper;
import com.cvtmarkov.javarestapi.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.stream.DoubleStream;

@Repository
public class CategoryRepository implements CrudRepository<Category>{
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query("SELECT * FROM category", new Object[]{}, new CategoryMapper());
    }

    @Override
    public Category findById(long id) {
            return jdbcTemplate.query("SELECT * FROM category WHERE id = ?", new Object[]{id}, new CategoryMapper())
                    .stream()
                    .findAny()
                    .orElseThrow(NotFoundException::new);
        }




    @Override
    public void deleteById(long id) {
        jdbcTemplate.update("DELETE FROM category WHERE id = ?", new Object[]{id}, new CategoryMapper());
    }

    @Override
    public Category save(@Nullable Category category) {
        jdbcTemplate.update("INSERT INTO category VALUES(?,?)", category.getId(), category.getName());
        return category;
    }

}
