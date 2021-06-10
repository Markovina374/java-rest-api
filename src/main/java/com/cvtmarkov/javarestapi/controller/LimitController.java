package com.cvtmarkov.javarestapi.controller;


import com.cvtmarkov.javarestapi.entity.MonthLimit;
import com.cvtmarkov.javarestapi.repository.CategoryRepository;
import com.cvtmarkov.javarestapi.repository.MonthLimitRepository;
import com.cvtmarkov.javarestapi.service.MonthLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 REST контроллер для работы с лимитами, принимает в себя:
 @see MonthLimitService - сервис для работы с "лимитаи" в базы данных
 @see MonthLimitRepository - репозиторий для работы с "лимитами" в базы данных
 */
@RestController
@RequestMapping("rest/monthLimit")
public class LimitController {
    @Autowired
    private final MonthLimitService monthLimitService;
    @Autowired
    private final MonthLimitRepository monthLimitRepository;

    public LimitController(MonthLimitService monthLimitService, MonthLimitRepository monthLimitRepository) {
        this.monthLimitService = monthLimitService;
        this.monthLimitRepository = monthLimitRepository;
    }

    /**
     *  Возвращает список всех месячных лимитов
     *  @return - список всех месячных лимитов
     */
    @GetMapping
    public List<MonthLimit> monthLimits() {
        return monthLimitRepository.findAll();
    }

    /**
     *  Возвращает месячный лимит по указанному месяцу
     *  @param month - номер месяца
     *  @return - результат поиска
     */
    @GetMapping("{month}")
    public MonthLimit getOneMonthLimit(@PathVariable("month") long month) {
        return monthLimitRepository.findById(month);
    }

    /**
     *  Сохраняет в базу данных новый лимит
     *  @param limit - новый месячный лимит
     *  @return - новый лимит
     */
    @PostMapping
    public MonthLimit createMonthLimit(@RequestBody MonthLimit limit) {
        return monthLimitRepository.save(limit);
    }

    /**
     *  Обновляет месячный лимит по указанному месяцу
     *  @param month - номер месяца
     *  @param monthLimit - новый лимит
     * @return - обновленный лимит
     */
    @PutMapping("{month}")
    public MonthLimit update(@PathVariable("month") long month,
                             @RequestBody MonthLimit monthLimit) {
        return monthLimitRepository.update(month, monthLimit);
    }

    /**
     *  Удаляет месячный лимит по заданному месяцу
     *  @param month - номер месяца
     *  @return - сообщение об удалении
     */
    @DeleteMapping("{month}")
    public String delete(@PathVariable("month") long month) {
        return monthLimitRepository.deleteById(month);
    }

    /**
     *  Проверяет превышен ли лимит за месяц. Если лимит превышен,
     *  то метод действует согласно заданному limit.method\у.
     *  @param month - номер месяца
     *  @return - сообщение о результате проверки
     */
    @GetMapping("/checkLimit{month}")
    public String checkLimitForMonth(@PathVariable("month") long month) {
        return monthLimitService.limitMethod(month);
    }

    /**
     *  Проверяет все месяцы на лимит, должны быть заполнены все поля в таблицах
     *
     * @return - результат поиска
     */
    @GetMapping("/checkLimit")
    public List<HashMap<String, String>> checkLimitAllMonth(){
        return monthLimitService.checkAllMonthForLimit();
    }


}
