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

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AccountDto(
        @Schema(example = "1")
        Long id,
        @NotBlank
        @Schema(example="SAVINGS",allowableValues = {"SAVINGS","CURRENT"})
        String accountType,
        @Schema(example = "SBI20240001")
        String accountNumber,
        @Schema(example = "SBI0001234")
        String branchIfsc,
        @Schema(example = "5000.0")
        double balance,
        @Schema(example = "0.0")
        double funds
){}


