/**
 * @author Zain I.
 **/

package com.zainimtiaz.nagarro.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "account_type")
    private String accountType;

    @Basic
    @Column(name = "account_number")
    private String accountNumber;

//    @OneToMany(mappedBy = "account")
//    private List<Statement> statement;
}
