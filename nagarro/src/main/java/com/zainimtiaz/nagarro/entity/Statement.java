/**
 * @author Zain I.
 * created on 13/12/2020
 **/

package com.zainimtiaz.nagarro.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "statement")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Statement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
//    private Account account;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    @Column(name = "account_id")
    private Long accountId;

    @Basic
    @Column(name = "datefield")
    private String dateField; //dd.MM.yyyy

    @Basic
    @Column(name = "amount")
    private String amount;
}
