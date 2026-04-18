package com.javaSpringProject.BankingService.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/healthz")
    public String health(){
        return "OK";
    }
}
