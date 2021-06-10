package com.cvtmarkov.javarestapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер перенаправляющий с "пустова URL'а" на страничку Swagger'а.
 */
@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping
    public String main() {
        return "redirect:/swagger-ui.html";
    }
}
