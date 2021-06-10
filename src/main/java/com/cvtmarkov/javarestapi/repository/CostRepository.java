package com.cvtmarkov.javarestapi.repository;

import com.cvtmarkov.javarestapi.entity.Cost;
import com.cvtmarkov.javarestapi.entity.mappers.CostMapper;
import com.cvtmarkov.javarestapi.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Репозитория для работы с Расходами в базе данных - имплементирующая:
 *
 * @see CRUDRepository - интерфейс
 * и принимающая:
 * @see JdbcTemplate
 */
@Repository
public class CostRepository implements CRUDRepository<Cost> {

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
    public String deleteById(long id) {
        if (jdbcTemplate.update("DELETE FROM cost WHERE id = ?", id) == 1) {
            return "Cost with this id = " + id + " deleted!";
        } else {
            return "Cost with this id = " + id + " was not found";
        }
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

    /**
     * Метод возвращает список расходов по заданной дате
     *
     * @param month - месяц
     * @param day   - день
     * @return - список Расходов
     */
    public List<Cost> findCostFromDate(int month, int day) {
        return jdbcTemplate.query("SELECT * FROM cost WHERE DAY(date) = ? AND MONTH(date) = ?", new Object[]{day, month}, new CostMapper());
    }


}
