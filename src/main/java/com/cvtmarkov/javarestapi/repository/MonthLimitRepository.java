package com.cvtmarkov.javarestapi.repository;

import com.cvtmarkov.javarestapi.entity.MonthLimit;
import com.cvtmarkov.javarestapi.entity.mappers.MonthLimitMapper;
import com.cvtmarkov.javarestapi.exception.NotFoundException;
import com.cvtmarkov.javarestapi.service.MonthLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
/**
 * Репозитория для работы с Месячными расходами в базе данных - имплементирующая:
 * @see CRUDRepository - интерфейс
 * и принимающая:
 * @see JdbcTemplate
 */
@Repository
public class MonthLimitRepository implements CRUDRepository<MonthLimit> {
    /**
     * Переменная находится в файле: resources/application.properties с ключем limit.method
     */
    @Value("${limit.method}")
    private String limitMethod;

    private BigDecimal valueOfLimit;

    @Autowired
    private ReportRepository reportRepository;
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
        if (jdbcTemplate.update("DELETE FROM month_limit WHERE numberMonth = ?", month) == 1) {
            return "MonthLimit with this number = " + month + " deleted!";
        } else {
            return "MonthLimit with this number = " + month + " was not found";
        }
    }

    @Override
    public MonthLimit save(MonthLimit limit) {

        limit.setLimit(monthLimitService.getLimitFromProperty());
        if(reportRepository.sumOfMonth(limit.getMonth())!=null) {
            limit.setSumOfMonth(reportRepository.sumOfMonth(limit.getMonth()));
        }
        jdbcTemplate.update("INSERT INTO month_limit VALUES(?,?,?)",
                limit.getMonth(), limit.getSumOfMonth(), limit.getLimit());
        return limit;
    }

    @Override
    public MonthLimit update(long month, @Nullable MonthLimit limit) {
        limit.setMonth((int) month);
        if (limitMethod.equals("adaptive")) {
            limit.setLimit(monthLimitService.getLimitFromProperty());
        }
        jdbcTemplate.update("UPDATE month_limit SET sum_of_month = ?, limit_of_month = ? WHERE numberMonth = ?",
                reportRepository.sumOfMonth(limit.getMonth()), limit.getLimit(), month);
        return limit;
    }


}

