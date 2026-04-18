package com.javaSpringProject.BankingService.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TransferDto(
        @Schema(example = "1")
        Long sourceId,

        @Schema(example = "2")
        Long targetId,

        @Schema(example = "1000.0")
        double amount
){}
