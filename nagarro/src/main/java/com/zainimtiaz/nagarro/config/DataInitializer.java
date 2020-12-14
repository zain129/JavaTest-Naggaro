/**
 * @author Zain I.
 * created on 12/12/2020
 **/
/*
package com.zainimtiaz.nagarro.config;

import com.zainimtiaz.nagarro.entity.Account;
import com.zainimtiaz.nagarro.entity.User;
import com.zainimtiaz.nagarro.repository.AccountRepository;
import com.zainimtiaz.nagarro.repository.UserMockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserMockRepository users;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static void logData(Account account) {
        log.debug(" Account :" + account.getAccountNumber() + " (" + account.getAccountType() + ")");
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("initializing accounts data...");
        List<Account> accountsList = new ArrayList<Account>();
        accountsList.add(new Account(1L, "current", "0012250016001"));
        accountsList.add(new Account(2L, "saving", "0012250016002"));
        accountsList.add(new Account(2L, "current", "0012250016003"));
        accountsList.add(new Account(2L, "current", "0012250016004"));
        accountsList.add(new Account(2L, "saving", "0012250016005"));

//        accountsList.forEach(account -> this.accountRepository
//                .saveAndFlush(Account.builder()
//                        .accountNumber(account.getAccountNumber())
//                        .accountType(account.getAccountType())
//                        .build()));

        log.debug("printing all accounts...");
        accountsList.forEach(DataInitializer::logData);
//        this.accountRepository.findAll()
//                .forEach(account ->
//                        log.debug(" Account :" + account.getAccountNumber() + " (" + account.getAccountType() + ")"));

        List<User> userList = new ArrayList<User>();
        userList.add(new User("testadmin",
                this.passwordEncoder.encode("adminpassword"),
                Arrays.asList("ROLE_USER", "ROLE_ADMIN")));

        userList.add(new User("testUser",
                this.passwordEncoder.encode("userpassword"),
                Arrays.asList("ROLE_USER")));

        log.debug("printing all users...");
        this.users.findAll().forEach(user -> log.debug(" Users :" + user.toString()));
    }
}
*/