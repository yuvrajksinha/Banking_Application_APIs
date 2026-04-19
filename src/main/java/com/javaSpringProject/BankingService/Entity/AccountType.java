package com.javaSpringProject.BankingService.Entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Type of bank account")
public enum AccountType {
    SAVINGS,
    CURRENT
}
