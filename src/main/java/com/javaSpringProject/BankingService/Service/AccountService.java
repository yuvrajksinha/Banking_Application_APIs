package com.javaSpringProject.BankingService.Service;

import com.javaSpringProject.BankingService.Dto.AccountDto;
import com.javaSpringProject.BankingService.Dto.UserDto;
import com.javaSpringProject.BankingService.Dto.UserRegistrationDto;

import java.util.List;

public interface AccountService {
    //Main registration
    UserDto createAccount(UserRegistrationDto userRegistrationDto);

    //account operations
    UserDto getUserProfile(Long userId);
    AccountDto getAccountById(Long id);
    String getAccountType(Long id);
    AccountDto addAccountById(Long id,AccountDto accountDto);
    AccountDto deposit(Long id, double amount);
    AccountDto withdraw(Long id,double amount);
    void transferMoney(Long sourceId,Long targetId,double amount);
    List<AccountDto> getAllAccounts();
    List<AccountDto> getAccountsByUserId(Long userId);
    void deleteAccount(Long id);

    List<UserDto> getAllUsersWithDetails();
}
