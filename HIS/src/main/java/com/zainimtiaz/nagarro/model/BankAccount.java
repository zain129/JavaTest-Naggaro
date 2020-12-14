package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "BANK_ACCOUNT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankAccount extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "title")
    private String title;

    @Column(name = "acc_number")
    private String accountNumber;

    @Column(name = "iban")
    private String iban;

    @Column(name = "branch")
    private String branch;

    public BankAccount() {
    }

    public BankAccount(BankAccount bankAccount) {
        this.setId(bankAccount.getId());
        this.title = bankAccount.getTitle();
        this.accountNumber = bankAccount.getAccountNumber();
        this.iban = bankAccount.getIban();
        this.branch = bankAccount.getBranch();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
