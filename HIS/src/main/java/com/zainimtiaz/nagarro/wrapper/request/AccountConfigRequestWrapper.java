package com.zainimtiaz.nagarro.wrapper.request;

public class AccountConfigRequestWrapper {

    //ASSETS
    long cash;

    long bank;

    long inventory;

    long accountReceivable;

    long palntEquipment;

    long furnitureFixture;

    //Liabilities
    long accountPayable;

    long taxPayable;

    long accuredSalary;

    long loan;

    long otherPayable;

    long parentType;

    //INCOME
    long income;

    long otherIncome;

    //COGS
    long costOfSale;

    //EXPENSE
    long generalExpense;
    long doctorExpense;

    public AccountConfigRequestWrapper() {
    }

/*    public AccountConfigRequestWrapper(String cash,String bank,String inventory,String accountReceivable,String palntEquipment,String furnitureFixture) {
        this.cash = cash;

        this.bank = bank;

        this.inventory = inventory;

        this.accountReceivable = accountReceivable;

        this.palntEquipment = palntEquipment;

        this.furnitureFixture = furnitureFixture;
    }*/

    public long getCash() {
        return cash;
    }

    public void setCash(long cash) {
        this.cash = cash;
    }

    public long getBank() {
        return bank;
    }

    public void setBank(long bank) {
        this.bank = bank;
    }

    public long getInventory() {
        return inventory;
    }

    public void setInventory(long inventory) {
        this.inventory = inventory;
    }

    public long getAccountReceivable() {
        return accountReceivable;
    }

    public void setAccountReceivable(long accountReceivable) {
        this.accountReceivable = accountReceivable;
    }

    public long getPalntEquipment() {
        return palntEquipment;
    }

    public void setPalntEquipment(long palntEquipment) {
        this.palntEquipment = palntEquipment;
    }

    public long getFurnitureFixture() {
        return furnitureFixture;
    }

    public void setFurnitureFixture(long furnitureFixture) {
        this.furnitureFixture = furnitureFixture;
    }

    public long getAccountPayable() {
        return accountPayable;
    }

    public void setAccountPayable(long accountPayable) {
        this.accountPayable = accountPayable;
    }

    public long getTaxPayable() {
        return taxPayable;
    }

    public void setTaxPayable(long taxPayable) {
        this.taxPayable = taxPayable;
    }

    public long getAccuredSalary() {
        return accuredSalary;
    }

    public void setAccuredSalary(long accuredSalary) {
        this.accuredSalary = accuredSalary;
    }

    public long getLoan() {
        return loan;
    }

    public void setLoan(long loan) {
        this.loan = loan;
    }

    public long getOtherPayable() {
        return otherPayable;
    }

    public void setOtherPayable(long otherPayable) {
        this.otherPayable = otherPayable;
    }

    public long getParentType() {
        return parentType;
    }

    public void setParentType(long parentType) {
        this.parentType = parentType;
    }

    public long getIncome() {
        return income;
    }

    public void setIncome(long income) {
        this.income = income;
    }

    public long getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(long otherIncome) {
        this.otherIncome = otherIncome;
    }

    public long getCostOfSale() {
        return costOfSale;
    }

    public void setCostOfSale(long costOfSale) {
        this.costOfSale = costOfSale;
    }

    public long getGeneralExpense() {
        return generalExpense;
    }

    public void setGeneralExpense(long generalExpense) {
        this.generalExpense = generalExpense;
    }

    public long getDoctorExpense() {
        return doctorExpense;
    }

    public void setDoctorExpense(long doctorExpense) {
        this.doctorExpense = doctorExpense;
    }
}
