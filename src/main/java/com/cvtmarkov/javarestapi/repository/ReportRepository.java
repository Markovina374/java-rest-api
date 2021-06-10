package com.cvtmarkov.javarestapi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Репозитория для работы с Категориями в базе данных принимающая:
 *
 * @see JdbcTemplate
 */
@Repository
public class ReportRepository {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public ReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Метод возвращает список дней в которые были сделаны расходы в данном методе
     *
     * @param month - месяц
     * @return - список дней
     */
    public List<Integer> findAllDayWhereExpensesMonth(int month) {
        return jdbcTemplate.queryForList("SELECT DISTINCT DAY(date) FROM cost WHERE MONTH(date) = ?", new Object[]{month}, Integer.class);
    }

    /**
     * Метод возвращает сумму расходов за определенную дату
     *
     * @param month - месяц
     * @param day   - день
     * @return - сумма
     */
    public BigDecimal sumOfDay(int month, int day) {
        return jdbcTemplate.queryForObject("SELECT SUM(value) FROM cost WHERE  DAY(date) = ? AND MONTH(date) = ? ", new Object[]{day, month}, BigDecimal.class);
    }

    /**
     * Метод возвращает сумму расходов за месяц
     *
     * @param month - месяц
     * @return - сумма
     */
    public BigDecimal sumOfMonth(int month) {
        return jdbcTemplate.queryForObject("SELECT SUM(value) FROM cost WHERE  MONTH(date) = ?", new Object[]{month}, BigDecimal.class);
    }

    /**
     * Метод возвращает номера месяцев в которые были сделаны расходы
     *
     * @return - список месяцев
     */
    public List<Integer> findAllMonthsWhereExpensesWere() {
        return jdbcTemplate.queryForList("SELECT DISTINCT month(date) From cost", Integer.class);
    }


}
