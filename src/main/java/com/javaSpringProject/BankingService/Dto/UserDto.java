package com.javaSpringProject.BankingService.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public record UserDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
        @NotBlank
        @Schema(example = "Rahul")
        String firstName,

        @Schema(example = "Kumar")
        String middleName,

        @Schema(example = "Sharma")
        String lastName,

        @Schema(example = "2000-01-01")
        LocalDate dateOfBirth,

        @Schema(example = "MALE")
        String gender,

        @Schema(example = "Mr.")
        String prefix,

        ContactDto contactDto,
        List<AccountDto> accounts
) {}
