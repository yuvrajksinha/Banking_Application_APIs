package com.javaSpringProject.BankingService.Controller;

import com.javaSpringProject.BankingService.Dto.AccountDto;
import com.javaSpringProject.BankingService.Dto.TransferDto;
import com.javaSpringProject.BankingService.Dto.UserDto;
import com.javaSpringProject.BankingService.Dto.UserRegistrationDto;
import com.javaSpringProject.BankingService.Service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //Registration REST API
    @PostMapping("/register")
    public ResponseEntity<UserDto> createAccount(@RequestBody UserRegistrationDto userRegistrationDto){
        return new ResponseEntity<>(accountService.createAccount(userRegistrationDto), HttpStatus.CREATED);
    }

    //Add Account by User id REST API
    @PostMapping("user/{userId}/addAccount")
    public ResponseEntity<AccountDto> addAccountById(@PathVariable Long userId,@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.addAccountById(userId,accountDto),HttpStatus.CREATED);
    }

    //Get User Profile REST API
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable Long userId){
        return ResponseEntity.ok(accountService.getUserProfile(userId));
    }

    //Transfer REST API
    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransferDto transferDto){
        accountService.transferMoney(transferDto.sourceId(), transferDto.targetId(), transferDto.amount());
        return ResponseEntity.ok("Transferred successfully!");
    }

    //Get Account REST API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    //Get Accounts with User Id REST API
    @GetMapping("user/{userId}/accounts")
    public ResponseEntity<List<AccountDto>> getAccountsByUserId(@PathVariable Long userId){
        List<AccountDto> accounts = accountService.getAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }

    //Deposit REST API
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id,@RequestBody Map<String,Double> request){
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.deposit(id,amount);
        return ResponseEntity.ok(accountDto);
    }

    //Withdraw REST API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,@RequestBody Map<String,Double> request){
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.withdraw(id,amount);
        return ResponseEntity.ok(accountDto);
    }

    //Get All Accounts REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts=accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    //Delete Account REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted successfully!");
    }

    //Get Account Type
    @GetMapping("/{id}/type")
    public ResponseEntity<Map<String,String>> getAccountType(@PathVariable Long id){
        String type = accountService.getAccountType(id);
        return ResponseEntity.ok(Map.of("Account type",type));
    }
}
