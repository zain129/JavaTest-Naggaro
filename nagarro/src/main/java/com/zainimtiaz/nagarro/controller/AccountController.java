/**
 * @author Zain I.
 * created on 12/12/2020
 **/

package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.config.jwt.JwtTokenProvider;
import com.zainimtiaz.nagarro.dto.AccountStatementDto;
import com.zainimtiaz.nagarro.dto.StatementDto;
import com.zainimtiaz.nagarro.service.AccountService;
import com.zainimtiaz.nagarro.service.CustomUserDetailsService;
import com.zainimtiaz.nagarro.util.GeneralUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
@Slf4j
public class AccountController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/statement/account/{accountId}")
    public ResponseEntity allData(@PathVariable("accountId") Long accountId) throws Exception {
        AccountStatementDto statement = accountService.getStatementById(accountId);

        Map result = new HashMap();
        result.put("account", statement.getAccountDto());
        result.put("statement", statement.getStatementDtos());
        return ok(result);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").split(" ")[1];
        String username = jwtTokenProvider.getUsername(token);
        return jwtTokenProvider.createToken(username, userDetailsService.getUserRoles(username));
    }

    @GetMapping("/statement/date/{accountId}/{dateFrom}/{dateTo}")
    public ResponseEntity dateOnly(@PathVariable("accountId") Long accountId,
                                   @PathVariable("dateFrom") String dateFrom,
                                   @PathVariable("dateTo") String dateTo) throws Exception {
        AccountStatementDto statement = accountService.getStatementByDate(accountId, dateFrom, dateTo);

        Map result = new HashMap();
        result.put("account", statement.getAccountDto());
        result.put("statement", statement.getStatementDtos());
        return ok(result);
    }

    @GetMapping("/statement/amount/{accountId}/{amountFrom}/{amountTo}")
    public ResponseEntity amountOnly(@PathVariable("accountId") Long accountId,
                                     @PathVariable("amountFrom") String amountFrom,
                                     @PathVariable("amountTo") String amountTo) throws Exception {
        AccountStatementDto statement = accountService.getStatementByAmount(accountId, amountFrom, amountTo);
        return ok(statement);
    }

    @GetMapping("/statement/all/{accountId}/{dateFrom}/{dateTo}/{amountFrom}/{amountTo}")
    public ResponseEntity allParams(@PathVariable("accountId") Long accountId,
                                    @PathVariable("dateFrom") String dateFrom,
                                    @PathVariable("dateTo") String dateTo,
                                    @PathVariable("amountFrom") String amountFrom,
                                    @PathVariable("amountTo") String amountTo) throws Exception {
        AccountStatementDto statement = accountService
                .getStatementByDateAndAmount(accountId, dateFrom, dateTo, amountFrom, amountTo);

        Map result = new HashMap();
        result.put("account", statement.getAccountDto());
        result.put("statement", statement.getStatementDtos());
        return ok(result);
    }
}
