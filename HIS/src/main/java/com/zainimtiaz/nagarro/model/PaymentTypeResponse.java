package com.zainimtiaz.nagarro.model;
import java.util.List;

public class PaymentTypeResponse extends PaginatedResponse{

    private List<PaymentType> accountTransactions;

    public List<PaymentType> getAccountTransactions() {
        return accountTransactions;
    }

    public void setAccountTransactions(List<PaymentType> accountTransactions) {
        this.accountTransactions = accountTransactions;
    }
}
