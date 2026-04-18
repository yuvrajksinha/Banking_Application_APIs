package com.javaSpringProject.BankingService.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserRegistrationDto(
        @Schema(description = "User Details")
        UserDto userDto,
        @Schema(description = "Initial Account Details")
        AccountDto accountDto,
        @Schema(description = "Contact Details")
        ContactDto contactDto
) {}
