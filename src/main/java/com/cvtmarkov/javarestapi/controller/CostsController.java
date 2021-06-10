package com.cvtmarkov.javarestapi.controller;

import com.cvtmarkov.javarestapi.entity.Cost;
import com.cvtmarkov.javarestapi.repository.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST контроллер для работы с расходами, принимает в себя:
 *
 * @see CostRepository - репозиторий для работы с "расходами" в базы данных
 */

@RestController
@RequestMapping("rest/costs")
public class CostsController {

    private final CostRepository costRepository;

    @Autowired
    public CostsController(CostRepository costRepository) {
        this.costRepository = costRepository;
    }

    /**
     * Возвращает список всех расходов
     *
     * @return - список всех расходов
     */
    @GetMapping
    public List<Cost> allCosts() {
        return costRepository.findAll();
    }

    /**
     * Возвращает расход по ID
     *
     * @param id - Идентификационный номер расхода
     * @return - найденный расход
     */
    @GetMapping("{id}")
    public Cost getOne(@PathVariable("id") long id) {
        return costRepository.findById(id);
    }

    /**
     * Сохраняет новый расход
     *
     * @param cost - новый расход
     * @return - новый расход
     */
    @PostMapping
    public Cost createCost(@RequestBody Cost cost) {
        if (cost.getDate() == null) {
            cost.setDate(LocalDate.now());
        }
        return costRepository.save(cost);
    }

    /**
     * Обновляет выбранный расход
     *
     * @param id   - Идентификационный номер расхода который хотим обновить
     * @param cost - обновленный расход
     * @return - обновленный расход
     */
    @PutMapping("{id}")
    public Cost updateCost(@PathVariable("id") long id,
                           @RequestBody Cost cost) {
        return costRepository.update(id, cost);
    }

    /**
     * Удаляет выбранный расход
     *
     * @param id Идентификационный номер расхода который хотим удалить
     * @return - сообщение при удалении
     */
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        return costRepository.deleteById(id);
    }

    /**
     * Поиск расходов по определенной дате
     *
     * @param month - месяц
     * @param day   - день
     * @return - список расходов в заданную дату
     */
    @GetMapping("month{month}/day{day}")
    public List<Cost> findCostByDate(@PathVariable("month") int month, @PathVariable("day") int day) {
        return costRepository.findCostFromDate(month, day);
    }


}

