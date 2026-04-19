package com.javaSpringProject.BankingService.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

public record AmountDto(

        @Schema(example = "1000.0", description = "Amount to deposit or withdraw")
        @Positive(message = "Amount must be greater than 0")
        double amount

) {}