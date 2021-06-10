package com.cvtmarkov.javarestapi.service;

import com.cvtmarkov.javarestapi.entity.MonthLimit;
import com.cvtmarkov.javarestapi.repository.MonthLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Сервис для работы с Месячными лимитами
 */
@Service
public class MonthLimitService {

    @Autowired
    MonthLimitRepository monthLimitRepository;

    /**
     * Переменная находится в файле: resources/application.properties с ключем limit
     */
    @Value("${limit}")
    private BigDecimal limit;

    /**
     * Переменная находится в файле: resources/application.properties с ключем limit.method
     */
    @Value("${limit.method}")
    private String limitMethod;

    /**
     * Метод аналогичен getter'у возвращает значение переменной лимит который указан в проперти файле
     *
     * @return - лимит
     */
    public BigDecimal getLimitFromProperty() {
        return this.limit;
    }

    /**
     * Метод проверяет на превышение месячного лимита и если он превышен -
     * производит манипуляции с месячным лимитом согласно заданному методу действий
     * и выводит соответствующее сообщение
     *
     * @param month - месяц
     * @return - сообщение
     */
    public String limitMethod(long month) {
        MonthLimit monthLimit = monthLimitRepository.findById(month);
        BigDecimal sum = monthLimit.getSumOfMonth();
        if (sum.compareTo(limit) > 0 && limitMethod.equals("adaptive")) {
            List<MonthLimit> monthLimitList = monthLimitRepository.findAll();
            int searchIndex = 0;
            BigDecimal value = limit.subtract(monthLimit.getSumOfMonth().subtract(limit));
            for (int i = 0; i < monthLimitList.size(); i++) {
                if (monthLimitList.get(i).equals(monthLimit)) {
                    searchIndex = ++i;
                    break;
                }
            }
            if (searchIndex == 12) searchIndex = 0;
            MonthLimit nextMonthLimit = monthLimitList.get(searchIndex);
            nextMonthLimit.setLimit(value);
            monthLimitRepository.updateForService(nextMonthLimit.getMonth(), nextMonthLimit);
            return "Because of exceeding the limit value of the limit for the next month is reduced to" + value;
        } else if (monthLimit.getSumOfMonth().compareTo(limit) > 0 && limitMethod.equals("increase")) {
            return "Your limit value is increased by the given one = " + monthLimit.getLimit() + " ! You can increase the limit on request PUT /rest/monthLimit/{month}";
        } else {
            return "Your limit value does not exceed the monthly amount " +
                    "or an incorrect method was selected for processing the limit";
        }
    }

    /**
     * Метод проверяет на превышение месячного лимита всех месяцев и если он превышен -
     * * производит манипуляции с месячным лимитом согласно заданному методу действий
     * * и выводит соответствующее сообщение
     *
     * @return - сообщение
     */
    public List<HashMap<String, String>> checkAllMonthForLimit() {
        List<MonthLimit> limitList = monthLimitRepository.findAll();
        List<HashMap<String, String>> hashMapList = new ArrayList<>();
        HashMap<String, String> oneMonthLimit = new HashMap<>();
        if (limitMethod.equals("adaptive") && limitList.size() < 11) {
            oneMonthLimit.put("Error", "Your monthly limits are not completely filled, please fill in and try again, or change the limit.method");
            hashMapList.add(oneMonthLimit);
            return hashMapList;
        } else {
            for (MonthLimit limit : limitList) {
                oneMonthLimit.put("Month = " + limit.getMonth(), limitMethod(limit.getMonth()));
            }
            hashMapList.add(oneMonthLimit);
        }
        return hashMapList;
    }


}
