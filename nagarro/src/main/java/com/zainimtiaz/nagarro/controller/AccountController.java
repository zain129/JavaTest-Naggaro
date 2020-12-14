/**
 * @author Zain I.
 * created on 12/12/2020
 **/

package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.dto.AccountStatementDto;
import com.zainimtiaz.nagarro.entity.Account;
import com.zainimtiaz.nagarro.exception.AccountNotFoundException;
import com.zainimtiaz.nagarro.model.AccountForm;
import com.zainimtiaz.nagarro.repository.AccountRepository;
import com.zainimtiaz.nagarro.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/account")
@Slf4j
public class AccountController {
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/statement/{accountId}")
    public ResponseEntity allData(@PathVariable("accountId") Long accountId) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime toDate = LocalDateTime.now(),
                fromDate = LocalDateTime.now().minusMonths(3);
        log.info("accountId: " + accountId);

        AccountStatementDto statement = accountService
                .getStatement(accountId, toDate.format(formatter), fromDate.format(formatter), null, null);
        return ok(statement);
    }

    @GetMapping("/statement/date/{accountId}/{dateFrom}/{dateTo}")
    public ResponseEntity dateOnly(@PathParam("accountId") Long accountId,
                                   @PathParam("dateFrom") String dateFrom,
                                   @PathParam("dateTo") String dateTo) throws Exception {
        AccountStatementDto statement = accountService
                .getStatement(accountId, dateFrom, dateTo, null, null);
        return ok(statement);
    }

    @GetMapping("/statement/amount/{accountId}/{amountFrom}/{amountTo}")
    public ResponseEntity amountOnly(@PathParam("accountId") Long accountId,
                                     @PathParam("amountFrom") String amountFrom,
                                     @PathParam("amountTo") String amountTo) throws Exception {
        AccountStatementDto statement = accountService
                .getStatement(accountId, null, null, amountFrom, amountTo);
        return ok(statement);
    }

    @GetMapping("/statement/all/{accountId}/{dateFrom}/{dateTo}/{amountFrom}/{amountTo}")
    public ResponseEntity allParams(@PathParam("accountId") Long accountId,
                                    @PathParam("dateFrom") String dateFrom,
                                    @PathParam("dateTo") String dateTo,
                                    @PathParam("amountFrom") String amountFrom,
                                    @PathParam("amountTo") String amountTo) throws Exception {
        AccountStatementDto statement = accountService
                .getStatement(accountId, dateFrom, dateTo, amountFrom, amountTo);
        return ok(statement);
    }

    @PostMapping("")
    public ResponseEntity save(@RequestBody AccountForm form, HttpServletRequest request) {
        Account account = this.accountRepository.save(Account.builder().accountNumber(form.getAccountNumber()).build());
        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/vehicles/{id}")
                        .buildAndExpand(account.getId())
                        .toUri())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) throws AccountNotFoundException {
        return ok(this.accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException()));
    }

}
