/**
 * @author Zain I.
 * created on 14/12/2020
 **/

package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.dto.AccountStatementDto;

public interface AccountService {
    /**
     * This method returns the statement of an account by Id only.
     * // If the request does not specify any parameter
     * // then the search will return three months back statement.
     *
     * @param accountId Required
     * @return
     */
    AccountStatementDto getStatementById(Long accountId) throws Exception;

    /**
     * This method returns the statement of an account in specified date range.
     *
     * @param accountId
     * @param fromDate
     * @param toDate
     * @return
     */
    AccountStatementDto getStatementByDate(Long accountId, String fromDate, String toDate) throws Exception;

    /**
     * This method returns the statement of an account in specified amount range.
     *
     * @param accountId
     * @param fromAmount
     * @param toAmount
     * @return
     */
    AccountStatementDto getStatementByAmount(Long accountId, String fromAmount, String toAmount) throws Exception;

    /**
     * This method returns the statement of an account.
     *
     * @param accountId
     * @param fromDate
     * @param toDate
     * @param fromAmount
     * @param toAmount
     * @return
     */
    AccountStatementDto getStatementByDateAndAmount
    (Long accountId, String fromDate, String toDate, String fromAmount, String toAmount) throws Exception;
}
