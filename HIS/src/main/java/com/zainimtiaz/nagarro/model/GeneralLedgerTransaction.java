package com.zainimtiaz.nagarro.model;

import javax.persistence.*;

@Entity
@Table(name = "GENERAL_LEDGER_TRANSACTION")
public class GeneralLedgerTransaction extends BaseEntity {

    @Column(name = "TRANSACTION_CATEGORY")
    private String transactionCategory;     // INVOICE, REFUND, or PAYMENT etc

    @Column(name = "TRANSACTION_ID") //this will from relevent prefixes
    private String transactionId;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "TRANSACTION_TYPE")
    private String transactionType; // BalanceTypeEnum

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEDGER_ID")
    private GeneralLedger generalLedger;


    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public GeneralLedger getGeneralLedger() {
        return generalLedger;
    }

    public void setGeneralLedger(GeneralLedger generalLedger) {
        this.generalLedger = generalLedger;
    }
}
