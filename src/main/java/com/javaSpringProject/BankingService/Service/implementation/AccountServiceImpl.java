package com.javaSpringProject.BankingService.Service.implementation;

import com.javaSpringProject.BankingService.Dto.AccountDto;
import com.javaSpringProject.BankingService.Dto.UserDto;
import com.javaSpringProject.BankingService.Dto.UserRegistrationDto;
import com.javaSpringProject.BankingService.Entity.Account;
import com.javaSpringProject.BankingService.Entity.AccountType;
import com.javaSpringProject.BankingService.Entity.User;
import com.javaSpringProject.BankingService.Exception.AccountException;
import com.javaSpringProject.BankingService.Mapper.AccountMapper;
import com.javaSpringProject.BankingService.Mapper.UserMapper;
import com.javaSpringProject.BankingService.Repository.AccountRepository;
import com.javaSpringProject.BankingService.Repository.UserRepository;
import com.javaSpringProject.BankingService.Service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountRepository accountRepository;
    private UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository,UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDto createAccount(UserRegistrationDto userRegistrationDto) {
        log.info("Creating new user with email={}", userRegistrationDto.contactDto().email());
        User user = UserMapper.mapToUserEntity(userRegistrationDto);
        User savedUser = userRepository.save(user);
        log.info("User created successfully with id={}", savedUser.getId());
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserProfile(Long userId) {
        log.info("Fetching user profile for id={}", userId);
        User user = userRepository.
                findById(userId).
                orElseThrow(()->{
                    log.error("User not found for id={}", userId);
                    return new AccountException("User not Found");
                });
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        log.info("Fetching account for id={}", id);
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()->{
                    log.error("Account not found for id={}", id);
                    return new AccountException("Account does not exist");
                });
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public String getAccountType(Long id) {
        log.info("Fetching account type for id={}", id);
        Account account = accountRepository.
                findById(id).
                orElseThrow(()->{
                    log.error("Account not found for id={}", id);
                    return new AccountException("Account not Found");
                });
        return account.getAccountType().name();
    }

    @Transactional
    @Override
    public AccountDto addAccountById(Long id, AccountDto accountDto) {
        log.info("Adding new account for userId={}", id);
        User user = userRepository.
                findById(id).
                orElseThrow(()->{
                    log.error("User not found for id={}", id);
                    return new AccountException("User not found");
                });
        Account newAccount = new Account();
        newAccount.setAccountNumber(accountDto.accountNumber());
        newAccount.setAccountType(accountDto.accountType());
        newAccount.setBranchIfsc(accountDto.branchIfsc());
        newAccount.setBalance(accountDto.balance());
        newAccount.setFunds(accountDto.funds());

        newAccount.setUser(user);

        Account savedAccount = accountRepository.save(newAccount);

        log.info("Account created with id={} for userId={}", savedAccount.getId(), id);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Transactional
    @Override
    public AccountDto deposit(Long id, double amount) {
        log.info("Depositing {} into account {}", amount, id);
        Account account = accountRepository
                .findByIdWithLock(id)
                .orElseThrow(()->{
                    log.error("Account not found for deposit id={}", id);
                    return new AccountException("Account does not exist");
                });
        account.setBalance(account.getBalance()+amount);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Transactional
    @Override
    public AccountDto withdraw(Long id, double amount) {
        log.info("Withdrawing {} from account {}", amount, id);
        Account account = accountRepository
                .findByIdWithLock(id)
                .orElseThrow(()->{
                    log.error("Account not found for withdraw id={}", id);
                    return new AccountException("Account does not exist");
                });

        if(account.getAccountType() == AccountType.SAVINGS){
            if(account.getBalance() < amount){
                log.error("Insufficient balance for account {}", id);
                throw new AccountException("Insufficient Balance");
            }
        } else {
            if((account.getBalance()+account.getFunds()) < amount){
                log.error("Insufficient funds for account {}", id);
                throw new AccountException("Insufficient Funds");
            }
        }

        account.setBalance(account.getBalance()-amount);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Transactional
    @Override
    public void transferMoney(Long sourceId, Long targetId, double amount) {
        log.info("Transferring {} from {} to {}", amount, sourceId, targetId);
        if(sourceId.equals(targetId)){
            log.error("Transfer failed: same account {}", sourceId);
            throw new AccountException("Cannot transfer to the same account");
        }
        Long firstId = Math.min(sourceId,targetId);
        Long secondId = Math.max(sourceId,targetId);
        accountRepository.findByIdWithLock(firstId).orElseThrow(()->new AccountException("Account "+firstId+" not found"));
        accountRepository.findByIdWithLock(secondId).orElseThrow(()->new AccountException("Account "+secondId+" not found"));

        Account source = accountRepository.findById(sourceId).get();
        Account target = accountRepository.findById(targetId).get();

        if(source.getAccountType() == AccountType.SAVINGS){
            if(source.getBalance() < amount){
                log.error("Insufficient balance for transfer from {}", sourceId);
                throw new AccountException("Insufficient Balance to transfer");
            }
        } else { //Account type is current
            if((source.getBalance()+source.getFunds())<amount){
                log.error("Insufficient funds for transfer from {}", sourceId);
                throw new AccountException("Insufficient Balance and funds");
            }
        }

        source.setBalance(source.getBalance()-amount);
        target.setBalance(target.getBalance()+amount);

        accountRepository.save(source);
        accountRepository.save(target);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        log.info("Fetching all accounts");
        List<Account> accounts = accountRepository.findAll();
        // return accounts.stream().map((account)->AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
        //OR
        // return accounts.stream().map(AccountMapper::mapToAccountDto).toList();
        //OR
        List<AccountDto> accountsDto = new ArrayList<>();
        for(Account account:accounts){
            AccountDto accountDto = AccountMapper.mapToAccountDto(account);
            accountsDto.add(accountDto);
        }
        return accountsDto;
    }

    @Override
    public List<AccountDto> getAccountsByUserId(Long userId) {
        log.info("Fetching accounts for userId={}", userId);
        User user = userRepository.
                findById(userId).
                orElseThrow(()->{
                    log.error("User not found for id={}", userId);
                    return new AccountException("User not Found");
                });
        return user.getAccounts().stream().
                map(AccountMapper::mapToAccountDto).toList();
    }

    @Transactional
    @Override
    public void deleteAccount(Long id) {
        log.info("Deleting account id={}", id);
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()->{
                    log.error("Account not found for deletion id={}", id);
                    return new RuntimeException("Account does not exist");
                });

        User user = account.getUser();

        if(user.getAccounts().size()<=1){
            log.info("Deleting user {} as it was last account", user.getId());
            userRepository.delete(user);
        } else {
            accountRepository.delete(account);
        }
    }

}
