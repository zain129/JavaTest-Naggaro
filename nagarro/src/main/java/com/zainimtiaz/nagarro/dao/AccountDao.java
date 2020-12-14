/**
 * @author Zain I.
 * created on 14/12/2020
 **/

package com.zainimtiaz.nagarro.dao;

import com.zainimtiaz.nagarro.entity.Account;

import java.util.List;

public interface AccountDao {
    List<Account> getAccount();

    Account getAccountById(Long accountId);
}
