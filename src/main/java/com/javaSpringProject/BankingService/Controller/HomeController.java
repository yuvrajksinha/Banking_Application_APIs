package com.javaSpringProject.BankingService.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Banking Service is Running 🚀";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
