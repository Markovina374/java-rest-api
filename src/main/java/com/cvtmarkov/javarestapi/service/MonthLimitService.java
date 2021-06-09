package com.cvtmarkov.javarestapi.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@Data
public class MonthLimitService {
    @Value("${limit}")
    BigDecimal limit;

    public BigDecimal a (){
        return this.limit;
    }




}
