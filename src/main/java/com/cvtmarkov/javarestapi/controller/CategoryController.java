package com.cvtmarkov.javarestapi.controller;

import com.cvtmarkov.javarestapi.entity.Category;
import com.cvtmarkov.javarestapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/category")
public class CategoryController {
    @Autowired
    private final CategoryRepository categoryRepository;


    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> allCategory(){
        return categoryRepository.findAll();
    }


    @GetMapping("{id}")
    public Category getOne(@PathVariable("id") long id){
        return categoryRepository.findById(id);
    }



    @PostMapping
    public Category create(@RequestBody Category category ){
        return categoryRepository.save(category);
    }

    @PutMapping("{id}")
    public Category update(@PathVariable("id") long id,
                       @RequestBody Category category){
        return categoryRepository.update(id, category);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id){
        categoryRepository.deleteById(id);
    }

}
