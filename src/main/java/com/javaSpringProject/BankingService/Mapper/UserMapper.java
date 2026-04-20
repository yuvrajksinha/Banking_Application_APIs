package com.javaSpringProject.BankingService.Mapper;

import com.javaSpringProject.BankingService.Dto.AccountDto;
import com.javaSpringProject.BankingService.Dto.ContactDto;
import com.javaSpringProject.BankingService.Dto.UserDto;
import com.javaSpringProject.BankingService.Dto.UserRegistrationDto;
import com.javaSpringProject.BankingService.Entity.Account;
import com.javaSpringProject.BankingService.Entity.Contact;
import com.javaSpringProject.BankingService.Entity.User;

import java.util.List;

public class UserMapper {
    public static User mapToUserEntity(UserRegistrationDto userRegistrationDto){
        User user = new User();
        user.setFirstName(userRegistrationDto.userDto().firstName());
        user.setMiddleName(userRegistrationDto.userDto().middleName());
        user.setLastName(userRegistrationDto.userDto().lastName());
        user.setDateOfBirth(userRegistrationDto.userDto().dateOfBirth());
        user.setGender(userRegistrationDto.userDto().gender());
        user.setPrefix(userRegistrationDto.userDto().prefix());

        Contact contact = new Contact();
        contact.setEmail(userRegistrationDto.contactDto().email());
        contact.setPhoneNumber1(userRegistrationDto.contactDto().phoneNumber1());
        contact.setPhoneNumber2(userRegistrationDto.contactDto().phoneNumber2());
        contact.setAddressLine1(userRegistrationDto.contactDto().addressLine1());
        contact.setAddressLine2(userRegistrationDto.contactDto().addressLine2());
        contact.setCity(userRegistrationDto.contactDto().city());
        contact.setLandmark(userRegistrationDto.contactDto().landmark());
        contact.setPincode(userRegistrationDto.contactDto().pincode());
        contact.setState(userRegistrationDto.contactDto().state());

        Account account = new Account();
        account.setAccountType(userRegistrationDto.accountDto().accountType());
        account.setAccountNumber(userRegistrationDto.accountDto().accountNumber());
        account.setBranchIfsc(userRegistrationDto.accountDto().branchIfsc());
        account.setBalance(userRegistrationDto.accountDto().balance());
        account.setFunds(userRegistrationDto.accountDto().funds());

        contact.setUser(user);
        user.setContact(contact);

        account.setUser(user);
        user.getAccounts().add(account);

        return user;
    }

    public static UserDto mapToUserDto(User user){
        ContactDto contactDto = null;
        if (user.getContact() != null) {
            contactDto = new ContactDto(user.getContact().getId(),
                    user.getContact().getEmail(),
                    user.getContact().getPhoneNumber1(),
                    user.getContact().getPhoneNumber2(),
                    user.getContact().getAddressLine1(),
                    user.getContact().getAddressLine2(),
                    user.getContact().getCity(),
                    user.getContact().getLandmark(),
                    user.getContact().getPincode(),
                    user.getContact().getState()
            );
        }
        List<AccountDto> accounts = user.getAccounts()
                .stream()
                .map(AccountMapper::mapToAccountDto)
                .toList();
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getDateOfBirth(),
                user.getGender(),
                user.getPrefix(),
                contactDto,
                accounts);
    }
}
