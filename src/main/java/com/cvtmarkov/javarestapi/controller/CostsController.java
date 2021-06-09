package com.cvtmarkov.javarestapi.controller;

import com.cvtmarkov.javarestapi.entity.Cost;
import com.cvtmarkov.javarestapi.repository.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("rest/costs")
public class CostsController {

    private final CostRepository costRepository;

    @Autowired
    public CostsController(CostRepository costRepository) {
        this.costRepository = costRepository;
    }


    @GetMapping
    public List<Cost> allCosts() {
        return costRepository.findAll();
    }


    @GetMapping("{id}")
    public Cost getOne(@PathVariable("id") long id) {
        return costRepository.findById(id);
    }


    @PostMapping
    public Cost create(@RequestBody Cost cost) {
        if (cost.getDate() == null) {
            cost.setDate(LocalDate.now());
        }
        return costRepository.save(cost);
    }

    @PutMapping("{id}")
    public Cost update(@PathVariable("id") long id,
                       @RequestBody Cost cost) {
        return costRepository.update(id, cost);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        return costRepository.deleteById(id);
    }

    @GetMapping("month{month}/day{day}")
    public List<Cost> findCostByDate(@PathVariable("month") int month, @PathVariable("day") int day) {
        return costRepository.findCostFromDate(month, day);
    }


}

