package com.javaSpringProject.BankingService.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record UserDto(
        @Schema(example = "1")
        Long id,

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

        ContactDto contactDto
) {}
