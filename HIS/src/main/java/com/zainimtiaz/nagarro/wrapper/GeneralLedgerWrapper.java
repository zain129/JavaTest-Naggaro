package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.GeneralLedger;

public class GeneralLedgerWrapper {

    private Long id;
    private String parentType;
    private String accountType;
    private String name;
    private String code;
    private String description;
    private String balanceType; //BalanceTypeEnum
    private String balance;

    /* Bank Account */
    private String title;
    private String accountNumber;
    private String iban;
    private String branch;

    public GeneralLedgerWrapper() {
    }

    public GeneralLedgerWrapper(Long id, String parentType, String accountType, String name, String code, String description,
                                String balanceType, String balance, String title, String accountNumber, String iban, String branch) {
        this.id = id;
        this.parentType = parentType;
        this.accountType = accountType;
        this.name = name;
        this.code = code;
        this.description = description;
        this.balanceType = balanceType;
        this.balance = balance;
        this.title = title;
        this.accountNumber = accountNumber;
        this.iban = iban;
        this.branch = branch;
    }

    public GeneralLedgerWrapper(GeneralLedger generalLedger) {
        this.id = generalLedger.getId();
        this.parentType = generalLedger.getParentType();
        this.accountType = generalLedger.getAccountType();
        this.name = generalLedger.getName();
        this.code = generalLedger.getCode();
        this.description = generalLedger.getDescription();
        this.balanceType = generalLedger.getBalanceType();
    }

    public GeneralLedgerWrapper(GeneralLedger generalLedger, String balance) {
        this.id = generalLedger.getId();
        this.parentType = generalLedger.getParentType();
        this.accountType = generalLedger.getAccountType();
        this.name = generalLedger.getName();
        this.code = generalLedger.getCode();
        this.description = generalLedger.getDescription();
        this.balanceType = generalLedger.getBalanceType();
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
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
