package com.cvtmarkov.javarestapi.service;

import com.cvtmarkov.javarestapi.entity.Cost;
import com.cvtmarkov.javarestapi.repository.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
public class MonthlyReportService {
    @Autowired
    private DateRepository dateRepository;

    public HashMap<HashMap<String, BigDecimal>, List<Cost>> monthlyAmount (int month){
        HashMap<HashMap<String, BigDecimal>, List<Cost>> hashMap = new HashMap<>();
        HashMap<String, BigDecimal> maximumHashmap = new HashMap<>();
        maximumHashmap.put("Sum for month", dateRepository.maxSumOfMonth(month));
        hashMap.put(maximumHashmap, dateRepository.findByMount(month));
        return hashMap;
    }
}
