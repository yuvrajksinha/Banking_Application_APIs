package com.javaSpringProject.BankingService.Dto;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class AccountDto {
//    private Long id;
//    private String accountHolderName;
//    private double balance;
//}

import com.javaSpringProject.BankingService.Entity.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record AccountDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
        @NotBlank
        @Schema(example="SAVINGS")
        AccountType accountType,
        @Schema(example = "SBI20240001")
        String accountNumber,
        @Schema(example = "SBI0001234")
        String branchIfsc,
        @PositiveOrZero
        @Schema(example = "5000.0")
        double balance,
        @Schema(example = "0.0")
        double funds
){}


