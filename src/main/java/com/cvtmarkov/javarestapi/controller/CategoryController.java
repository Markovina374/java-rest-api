package com.cvtmarkov.javarestapi.controller;

import com.cvtmarkov.javarestapi.entity.Category;
import com.cvtmarkov.javarestapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
  REST контроллер для работы с Категориями, принимает в себя:
    @see CategoryRepository - репозиторий для работы с "категориями" в базы данных
 */



@RestController
@RequestMapping("rest/category")
public class CategoryController {
    @Autowired
    private final CategoryRepository categoryRepository;


    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Возвращает список всех категорий
     * @return - список всех категорий
     */
    @GetMapping
    public List<Category> allCategory() {
        return categoryRepository.findAll();
    }

    /**
     *  Возвращает категорию по заданному полю
     *  @param id - Идентификационный номер категории
     * @return - результат поиска
     */
    @GetMapping("{id}")
    public Category getOne(@PathVariable("id") long id) {
        return categoryRepository.findById(id);
    }

    /**
     * Сохраняет категорию
     * @param category - новая категорию
     * @return - новая категория
     */
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    /**
     *  Обновляет выбранную категорию
     * @param id - Идентификационный номер категории которую хотим обновить
     * @param category - обновленная категория
     * @return - обновленная категория
     */
    @PutMapping("{id}")
    public Category updateCategory(@PathVariable("id") long id,
                           @RequestBody Category category) {
        return categoryRepository.update(id, category);
    }

    /**
     * Удаляет выбранную категорию
     * @param id Идентификационный номер категории которую хотим удалить
     * @return - сообщение об удалении
     */
    @DeleteMapping("{id}")
    public String deleteCategoryById(@PathVariable("id") long id) {
        return categoryRepository.deleteById(id);
    }

    /**
     * Возвращает сумму расходов по определенной категории по дате
     * @param id - Идентификационный номер категории которая нас интересует
     * @param day - День расхода
     * @param month - Месяц расхода
     * @return - сумма расходов
     */
    @GetMapping("{id}/sum/month{month}/day{day}")
    public BigDecimal amountOfExpensesOfCategoryForSelectedDate(@PathVariable("id") long id,
                                           @PathVariable("month") int month,
                                           @PathVariable("day") int day) {
        return categoryRepository.sumOfCategoryOfDate(id, month, day);
    }

    /**
     * Возвращает общую сумму расходов по данной категории
     * @param id - Идентификационный номер категории которая нас интересует
     * @return сумма расходов по заданной категории
     */
    @GetMapping("{id}/sum")
    public BigDecimal amountOfExpensesOfCategory(@PathVariable("id") long id) {
        return categoryRepository.sumOfCategory(id);
    }

}
