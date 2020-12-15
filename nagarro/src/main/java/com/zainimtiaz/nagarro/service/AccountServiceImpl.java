/**
 * @author Zain I.
 * created on 14/12/2020
 **/

package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.dto.AccountDto;
import com.zainimtiaz.nagarro.dto.AccountStatementDto;
import com.zainimtiaz.nagarro.dto.StatementDto;
import com.zainimtiaz.nagarro.exception.AccountNumberMissingException;
import com.zainimtiaz.nagarro.exception.InvalidAmountRangeException;
import com.zainimtiaz.nagarro.exception.InvalidDateRangeException;
import com.zainimtiaz.nagarro.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String sqlAcountById = "SELECT * FROM ACCOUNT WHERE id = ?";
    //    private final String sqlStatementByDate = "SELECT * FROM STATEMENT WHERE account_id = ? AND CAST(datefield AS DATETIME) BETWEEN ? AND ?";
    private String sqlStatementByDate = "SELECT s.* FROM STATEMENT s WHERE account_id = ? ";
    //        + " AND (CONVERT(date, \\`datefield\\`, 4) BETWEEN '?' AND '?')";
    private final String sqlStatementByAmount = "SELECT * FROM STATEMENT WHERE account_id = ? "
            + " AND CAST(amount AS DECIMAL(16, 4)) BETWEEN ? AND ?";
    private final String sqlStatementByDateAndAmount = "SELECT * FROM STATEMENT WHERE account_id = ? "
            + " AND (CAST(amount AS DECIMAL(16, 4)) BETWEEN ? AND ?) ";
//            + " AND (CAST(datefield AS DATETIME) BETWEEN ? AND ?) ";

    @Override
    public AccountStatementDto getStatementById(Long accountId) throws Exception {
        validateAccountId(accountId);

        // 1. If the request does not specify any parameter then the search will return three months back statement
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime toDate = LocalDateTime.now(),
                fromDate = LocalDateTime.now().minusMonths(3);

        return getStatementByDate(accountId, fromDate.format(formatter), toDate.format(formatter));
    }

    @Override
    public AccountStatementDto getStatementByDate(Long accountId, String fromDate, String toDate) throws Exception {
        validateDateRange(fromDate, toDate);

        AccountDto accountDto = jdbcTemplate.queryForObject(
                sqlAcountById,
                new Object[]{accountId},
                (rs, rowNum) ->
                        new AccountDto(
                                rs.getLong("id"),
                                rs.getString("account_type"),
                                rs.getString("account_number")
                        ));

        String hashedAccNmbr = GeneralUtil.calculateHashValue(accountDto.getAccountNumber());
        accountDto.setAccountNumber(hashedAccNmbr);

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date fromDte = dateFormat.parse(fromDate), toDte = dateFormat.parse(toDate);

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlStatementByDate, new Object[]{accountId});
        List<StatementDto> statementDtos = new ArrayList<>();

        for (Map row : rows) {
            statementDtos.add(
                    new StatementDto(
                            ((Integer) row.get("ID")).longValue(),
                            dateFormat.parse((String) row.get("datefield")),
                            new BigDecimal(row.get("amount") + "")
                    ));
        }

        statementDtos = statementDtos.stream()
                .filter(statement ->
                        ( (statement.getDateField().after(fromDte) && statement.getDateField().before(toDte))
                                || statement.getDateField().equals(fromDte) || statement.getDateField().equals(toDte)))
                .collect(Collectors.toList());

        AccountStatementDto accountStatementDto = new AccountStatementDto(accountDto, statementDtos);
        return accountStatementDto;
    }

    @Override
    public AccountStatementDto getStatementByAmount(Long accountId, String fromAmount, String toAmount) throws Exception {
        validateAmountRange(fromAmount, toAmount);

        AccountDto accountDto = jdbcTemplate.queryForObject(
                sqlAcountById,
                new Object[]{accountId},
                (rs, rowNum) ->
                        new AccountDto(
                                rs.getLong("id"),
                                rs.getString("account_type"),
                                rs.getString("account_number")
                        ));
        String hashedAccNmbr = GeneralUtil.calculateHashValue(accountDto.getAccountNumber());
        accountDto.setAccountNumber(hashedAccNmbr);

        List<Map<String, Object>> rows = jdbcTemplate.queryForList
                (sqlStatementByAmount, new Object[]{accountId, fromAmount, toAmount});

        List<StatementDto> statementDtos = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        for (Map row : rows) {
            statementDtos.add(
                    new StatementDto(
                            ((Integer) row.get("ID")).longValue(),
                            simpleDateFormat.parse((String) row.get("datefield")),
                            new BigDecimal(row.get("amount") + "")
                    ));
        }

        AccountStatementDto accountStatementDto = new AccountStatementDto(accountDto, statementDtos);
        return accountStatementDto;
    }

    @Override
    public AccountStatementDto getStatementByDateAndAmount
            (Long accountId, String fromDate, String toDate, String fromAmount, String toAmount) throws Exception {
        // The request should specify the account id.
        validateAccountId(accountId);

        // The request can specify from date and to date (the date range).
        validateDateRange(fromDate, toDate);

        // The request can specify from amount and to amount (the amount range).
        validateAmountRange(fromAmount, toAmount);

        AccountDto accountDto = jdbcTemplate.queryForObject(
                sqlAcountById,
                new Object[]{accountId},
                (rs, rowNum) ->
                        new AccountDto(
                                rs.getLong("id"),
                                rs.getString("account_type"),
                                rs.getString("account_number")
                        ));
        String hashedAccNmbr = GeneralUtil.calculateHashValue(accountDto.getAccountNumber());
        accountDto.setAccountNumber(hashedAccNmbr);

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date fromDte = dateFormat.parse(fromDate), toDte = dateFormat.parse(toDate);

        List<Map<String, Object>> rows = jdbcTemplate.queryForList
                (sqlStatementByDateAndAmount, new Object[]{accountId, fromAmount, toAmount});

        List<StatementDto> statementDtos = new ArrayList<>();

        for (Map row : rows) {
            statementDtos.add(
                    new StatementDto(
                            ((Integer) row.get("ID")).longValue(),
                            dateFormat.parse((String) row.get("datefield")),
                            new BigDecimal(row.get("amount") + "")
                    ));
        }

        statementDtos = statementDtos.stream()
                .filter(statement ->
                        ( (statement.getDateField().after(fromDte) && statement.getDateField().before(toDte))
                                || statement.getDateField().equals(fromDte) || statement.getDateField().equals(toDte)))
                .collect(Collectors.toList());

        AccountStatementDto accountStatementDto = new AccountStatementDto(accountDto, statementDtos);
        return accountStatementDto;
    }

    private void validateAccountId(Long accountId) throws AccountNumberMissingException {
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

        if (GeneralUtil.isNullorEmpty(fromAmount) && GeneralUtil.isNullorEmpty(toAmount)) {
            bothAmountsMissing = true;
        }

        if (!GeneralUtil.isNullorEmpty(fromAmount) && !GeneralUtil.isNullorEmpty(toAmount)) {
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
