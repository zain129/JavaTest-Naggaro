/**
 * @author Zain I.
 * created on 14/12/2020
 **/

package com.zainimtiaz.nagarro.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long accountId;
    private String accountType;
    private String accountNumber;

    @Override
    public String toString() {
        return "{ \"accountId:\" \"" + accountId + "\"" +
                ", \"accountType:\" \"" + accountType + "\"" +
                ", \"accountNumber:\" \"" + accountNumber + "\" }";
    }
}
