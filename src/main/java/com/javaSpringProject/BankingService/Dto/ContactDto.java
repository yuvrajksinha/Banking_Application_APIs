package com.javaSpringProject.BankingService.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ContactDto(
        @Schema(example = "1")
        Long id,

        @Schema(example = "rahul.sharma@email.com")
        String email,

        @Schema(example = "9876543210")
        String phoneNumber1,

        @Schema(example = "9123456780")
        String phoneNumber2,

        @Schema(example = "123 MG Road")
        String addressLine1,

        @Schema(example = "Near Metro Station")
        String addressLine2,

        @Schema(example = "Bangalore")
        String city,

        @Schema(example = "Near Mall")
        String landmark,

        @Schema(example = "560001")
        String pincode,

        @Schema(example = "Karnataka")
        String state
) {}
