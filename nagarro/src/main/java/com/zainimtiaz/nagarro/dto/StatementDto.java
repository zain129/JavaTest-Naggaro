/**
 * @author Zain I.
 * created on 14/12/2020
 **/

package com.zainimtiaz.nagarro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatementDto {
    private Long id;
    private Date dateField; //dd.MM.yyyy
    private BigDecimal amount;
}
