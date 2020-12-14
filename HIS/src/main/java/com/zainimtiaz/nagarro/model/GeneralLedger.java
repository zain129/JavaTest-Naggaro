package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GENERAL_LEDGER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralLedger  extends BaseEntity {

    @Column(name = "PARENT_TYPE")
    private String parentType;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "NAME")
    private String name;

    @NaturalId
    @Column(name = "CODE", unique = true, nullable = false, updatable = false)
    private String code; //Prefix from

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "BALANCE_TYPE")
    private String balanceType; //BalanceTypeEnum

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_id")
    private BankAccount bankAccount;

    @OneToMany(mappedBy = "generalLedger")
    @JsonBackReference
    private List<GeneralLedgerTransaction> generalLedgerTransactions;

    public GeneralLedger() {
    }

    public GeneralLedger(GeneralLedger generalLedger) {
        this.setId(generalLedger.getId());
        this.parentType = generalLedger.getParentType();
        this.accountType = generalLedger.getAccountType();
        this.name = generalLedger.getName();
        this.code = generalLedger.getCode();
        this.description = generalLedger.getDescription();
        this.bankAccount = generalLedger.getBankAccount();
    }


    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public List<GeneralLedgerTransaction> getGeneralLedgerTransactions() {
        return generalLedgerTransactions;
    }

    public void setGeneralLedgerTransactions(List<GeneralLedgerTransaction> generalLedgerTransactions) {
        this.generalLedgerTransactions = generalLedgerTransactions;
    }
}
