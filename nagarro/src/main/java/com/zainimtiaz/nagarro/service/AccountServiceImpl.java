/**
 * @author Zain I.
 * created on 14/12/2020
 **/

package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.dto.AccountDto;
import com.zainimtiaz.nagarro.dto.AccountStatementDto;
import com.zainimtiaz.nagarro.dto.StatementDto;
import com.zainimtiaz.nagarro.entity.Account;
import com.zainimtiaz.nagarro.entity.Statement;
import com.zainimtiaz.nagarro.exception.AccountNumberMissingException;
import com.zainimtiaz.nagarro.exception.InvalidAmountRangeException;
import com.zainimtiaz.nagarro.exception.InvalidDateRangeException;
import com.zainimtiaz.nagarro.repository.AccountRepository;
import com.zainimtiaz.nagarro.repository.StatementRepository;
import com.zainimtiaz.nagarro.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.DatatypeConverter;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private AccountRepository accountRepository;

    @PersistenceContext
    EntityManager em;

    @Override
    public AccountStatementDto getStatement(Long accountId, String fromDate, String toDate, String fromAmount, String toAmount)
            throws Exception {
        // The request should specify the account id.
        validateAccountNumber(accountId);

        // The request can specify from date and to date (the date range).
        validateDateRange(fromDate, toDate);

        // The request can specify from amount and to amount (the amount range).
        validateAmountRange(fromAmount, toAmount);

        Account account = accountRepository.getOne(accountId);
        if (account == null) {
            throw new AccountNumberMissingException("'Account Id' is incorrect.");
        }

        // 1. If the request does not specify any parameter then the search will return three months back statement
//        if (GeneralUtil.isNullorEmpty(fromDate) && GeneralUtil.isNullorEmpty(toDate)) {
        if (GeneralUtil.isNullorEmpty(fromAmount) && GeneralUtil.isNullorEmpty(toAmount)) {
            try {
//                    String jql = "select o from " + Statement.class + " o";
//                    jql += " order by o.dateField asc"; // + new SimpleDateFormat("dd.MM.yyyy").parse(dateField);
//                    statements = (List<Statement>) em.createQuery(jql).getResultList();

                List<Statement> statements = statementRepository.findByAccount(account);
                List<StatementDto> statementDtos = new ArrayList<>();

                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(account.getAccountNumber().getBytes());
                byte[] digest = md.digest();

                AccountDto accountDto = new AccountDto();
                accountDto.setAccountId(account.getId());
                accountDto.setAccountType(account.getAccountType());
                accountDto.setAccountNumber(DatatypeConverter.printHexBinary(digest).toUpperCase());

                DateFormat txnDateFormat = new SimpleDateFormat("dd.MM.yyyy");

                for (Statement statement : statements) {
                    StatementDto statementDto = new StatementDto();
                    statementDto.setId(statement.getId());
                    statementDto.setDateField(txnDateFormat.parse(statement.getDateField()));
                    statementDto.setAmount(new BigDecimal(statement.getAmount()));
                    statementDtos.add(statementDto);
                }

                AccountStatementDto accountStatementDto = new AccountStatementDto(accountDto, statementDtos);
                return accountStatementDto;
            } catch (Exception ex) {
                throw new Exception("Error while getting statement...");
            }

//            }
        }
//        em.createNamedQuery();

        return null;
    }

    private void validateAccountNumber(Long accountId) throws AccountNumberMissingException {
        if (accountId == null) {
            throw new AccountNumberMissingException("Required parameter 'Account Id' is missing.");
        }
        if (accountId < 1) {
            throw new AccountNumberMissingException("'Account Id' is invalid.");
        }
    }

    private void validateDateRange(String fromDate, String toDate) throws InvalidDateRangeException, ParseException {
        boolean bothDatesPresent = false, bothDatesMissing = false;

        if (fromDate == null && toDate == null) {
            bothDatesMissing = true;
        }

        if (fromDate != null && toDate != null) {
            bothDatesPresent = true;
        }

        if (!bothDatesMissing && !bothDatesPresent) {
            throw new InvalidDateRangeException("Please either provide both 'From Date' and 'To Date' or provide none.");
        }

        if (bothDatesPresent) {
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date fromDte = null, toDte = null;
            try {
                fromDte = format.parse(fromDate);
            } catch (ParseException ex) {
                throw new ParseException("Invalid 'From Date' value. Please provide date in format 'dd.MM.yyyy' (e.g 14.12.2020).",
                        ex.getErrorOffset());
            }

            try {
                toDte = format.parse(fromDate);
            } catch (ParseException ex) {
                throw new ParseException("Invalid 'To Date' value. Please provide date in format 'dd.MM.yyyy' (e.g 14.12.2020)."
                        , ex.getErrorOffset());
            }

            if (fromDte.after(toDte)) {
                throw new InvalidDateRangeException("Invalid date range. 'From Date' is after the 'To Date'.");
            }
        }
    }

    private void validateAmountRange(String fromAmount, String toAmount) throws InvalidAmountRangeException {
        boolean bothAmountsPresent = false, bothAmountsMissing = false;

        if (fromAmount.trim().isEmpty() && toAmount.trim().isEmpty()) {
            bothAmountsMissing = true;
        }

        if (!fromAmount.trim().isEmpty() && !toAmount.trim().isEmpty()) {
            bothAmountsPresent = true;
        }

        if (!bothAmountsMissing && !bothAmountsPresent) {
            throw new InvalidAmountRangeException("Please either provide both 'From Amount' and 'To Amount' or provide none.");
        }

        if (bothAmountsPresent) {
            BigDecimal fromAmt = new BigDecimal(fromAmount),
                    toAmt = new BigDecimal(toAmount);

            if (fromAmt.compareTo(toAmt) > 0) {
                throw new InvalidAmountRangeException("Invalid date range. 'From Amount' is greater than 'To Amount'.");
            }

//        if (fromAmt.compareTo(BigDecimal.ZERO) < 0) {
//            throw new InvalidAmountRangeException("'From Amount' should be a positive amount.");
//        }
//
//        if (toAmt.compareTo(BigDecimal.ZERO) < 0) {
//            throw new InvalidAmountRangeException("'To Amount' should be a positive amount.");
//        }
        }
    }
}
