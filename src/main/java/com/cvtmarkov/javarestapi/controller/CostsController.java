package com.cvtmarkov.javarestapi.controller;

import com.cvtmarkov.javarestapi.entity.Cost;
import com.cvtmarkov.javarestapi.repository.CostRepository;
import org.springframework.beans.BeanUtils;
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
        public List<Cost> allCosts(){
            return costRepository.findAll();
        }


        @GetMapping("{id}")
        public Cost getOne(@PathVariable("id") Cost cost){
            return cost;
        }



        @PostMapping
        public Cost create(@RequestBody Cost cost ){
            if(cost.getDate()==null) {
                cost.setDate(LocalDate.now());
            }return costRepository.save(cost);
        }

        @PutMapping("{id}")
        public Cost update(@PathVariable("id") Cost costFromDB,
                           @RequestBody Cost cost){
            BeanUtils.copyProperties(cost, costFromDB, "id");
            return costRepository.save(costFromDB);
        }
        @DeleteMapping("{id}")
        public void delete(@PathVariable ("id") Cost cost){
            costRepository.deleteById(cost.getId());
        }



    }

