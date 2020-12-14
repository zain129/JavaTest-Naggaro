/**
 * @author Zain I.
 **/

package com.zainimtiaz.nagarro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountForm {
    private String accountType;
    private String accountNumber;
}