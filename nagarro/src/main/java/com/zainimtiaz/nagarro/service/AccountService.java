/**
 * @author Zain I.
 * created on 14/12/2020
 **/

package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.dto.AccountStatementDto;
import com.zainimtiaz.nagarro.dto.StatementDto;

import java.util.List;

public interface AccountService {

    /**
     * This method returns the statement of an account.
     *
     * @param accountId  Required
     * @param fromDate   Optional
     * @param toDate     Optional
     * @param fromAmount Optional
     * @param toAmount   Optional
     * @return
     */
    AccountStatementDto getStatement(Long accountId, String fromDate, String toDate, String fromAmount, String toAmount)
            throws Exception;

    /**
     *
     *
     *
     * - If the request does not specify any parameter then the search will return three months
     * back statement.
     * - If the parameters are invalid a proper error message should be sent to user.
     * - The account number should be hashed before sent to the user.
     * - All the exceptions should be handled on the server properly
     */
}
