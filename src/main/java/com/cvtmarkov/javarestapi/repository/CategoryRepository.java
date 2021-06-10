package com.cvtmarkov.javarestapi.repository;

import com.cvtmarkov.javarestapi.entity.Category;
import com.cvtmarkov.javarestapi.entity.mappers.CategoryMapper;
import com.cvtmarkov.javarestapi.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Репозитория для работы с Категориями в базе данных - имплементирующая:
 *
 * @see CRUDRepository - интерфейс
 * и принимающая:
 * @see JdbcTemplate
 */
@Repository
public class CategoryRepository implements CRUDRepository<Category> {

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
    public String deleteById(long id) {
        if (jdbcTemplate.update("DELETE FROM category WHERE id = ? ", id) == 1) {
            jdbcTemplate.update("DELETE FROM cost WHERE category_id IS NULL");
            return "Category with this id = " + id + " deleted!";
        } else {
            return "Category with this id = " + id + " was not found!!";
        }
    }

    @Override
    public Category save(@Nullable Category category) {
        jdbcTemplate.update("INSERT INTO category VALUES(?,?)", category.getId(), category.getName());
        return category;
    }

    @Override
    public Category update(long id, Category newCategory) {
        newCategory.setId(id);
        jdbcTemplate.update("UPDATE category SET name = ? WHERE id = ?", newCategory.getName(), id);
        return newCategory;
    }

    /**
     * Метод возвращает список id категорий в заданной дате
     *
     * @param month - месяц
     * @param day   - день
     * @return - список id
     */
    public List<Integer> findCategoryByDate(int month, int day) {
        return jdbcTemplate.queryForList("SELECT DISTINCT category_id FROM cost WHERE DAY(date) = ? AND MONTH(date) = ?", new Object[]{day, month}, Integer.class);
    }

    /**
     * Метод возвращает сумму всех расходов по заданной категории
     *
     * @param id - id категории
     * @return - сумма
     */
    public BigDecimal sumOfCategory(long id) {
        return jdbcTemplate.queryForObject("SELECT SUM(value) FROM cost WHERE  category_id = ?", new Object[]{id}, BigDecimal.class);
    }

    /**
     * Метод возвращает сумму всех расходов по заданной категории и дате
     *
     * @param id    - id категории
     * @param month - месяц
     * @param day   - день
     * @return - сумма
     */
    public BigDecimal sumOfCategoryOfDate(long id, int month, int day) {
        return jdbcTemplate.queryForObject("SELECT SUM(value) FROM cost WHERE  DAY(date) = ? AND MONTH(date) = ? AND category_id = ?", new Object[]{day, month, id}, BigDecimal.class);
    }


}
