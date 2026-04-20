package com.javaSpringProject.BankingService.Config;

import com.javaSpringProject.BankingService.Dto.*;
import com.javaSpringProject.BankingService.Entity.AccountType;
import com.javaSpringProject.BankingService.Service.AccountService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(AccountService accountService,
                               JdbcTemplate jdbcTemplate) {

        return args -> {

            jdbcTemplate.execute("TRUNCATE TABLE accounts RESTART IDENTITY CASCADE");
            jdbcTemplate.execute("TRUNCATE TABLE contacts RESTART IDENTITY CASCADE");
            jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY CASCADE");

            System.out.println("Database reset (IDs restarted)");

            // User 1
            accountService.createAccount(new UserRegistrationDto(
                    new UserDto(null, "Arjun", "", "Reddy",
                            LocalDate.of(1997, 11, 5),
                            "MALE", "Mr.", null,null),

                    new AccountDto(null, AccountType.SAVINGS,
                            "ACC1001", "ICIC0001234",
                            7000.0, 0.0),

                    new ContactDto(null, "arjun.reddy@example.com",
                            "9012345678", "",
                            "Banjara Hills", "",
                            "Hyderabad", "Near Mall",
                            "500034", "Telangana")
            ));

            // User 2
            accountService.createAccount(new UserRegistrationDto(
                    new UserDto(null, "Sneha", "", "Kapoor",
                            LocalDate.of(1999, 3, 15),
                            "FEMALE", "Ms.", null,null),

                    new AccountDto(null, AccountType.CURRENT,
                            "ACC2002", "HDFC0005678",
                            3000.0, 5000.0),

                    new ContactDto(null, "sneha.kapoor@example.com",
                            "9123456789", "",
                            "Andheri West", "",
                            "Mumbai", "Near Station",
                            "400053", "Maharashtra")
            ));

            // User 3
            var user3 = accountService.createAccount(new UserRegistrationDto(
                    new UserDto(null, "Vikram", "", "Singh",
                            LocalDate.of(1995, 8, 20),
                            "MALE", "Mr.", null,null),

                    new AccountDto(null, AccountType.SAVINGS,
                            "ACC3003", "SBI0004321",
                            10000.0, 0.0),

                    new ContactDto(null, "vikram.singh@example.com",
                            "9988776655", "",
                            "MG Road", "",
                            "Bangalore", "Near Metro",
                            "560001", "Karnataka")
            ));

            // Add second account to user 3
            accountService.addAccountById(user3.id(),
                    new AccountDto(null, AccountType.CURRENT,
                            "ACC3004", "SBI0004321",
                            2000.0, 8000.0));

            System.out.println("Demo data loaded successfully");
        };
    }
}