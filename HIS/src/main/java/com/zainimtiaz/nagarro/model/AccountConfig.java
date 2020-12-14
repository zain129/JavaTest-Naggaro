package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "ACCOUNT_CONFIG")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

//ASSETS
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="CASH_ID")
    private GeneralLedger cash;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="BANK_ID")
    private GeneralLedger bank;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="INVENTORY_ID")
    private GeneralLedger inventory;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ACCOUNT_RECEIVABLE_ID")
    private GeneralLedger accountReceivable;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="PLANT_EQUIPMENT_ID")
    private GeneralLedger palntEquipment;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="FURNITURE_FIXTURE_ID")
    private GeneralLedger furnitureFixture;

//Liabilities
   @OneToOne(cascade=CascadeType.ALL)
   @JoinColumn(name="ACCOUNT_PAYABLE_ID")
    private GeneralLedger accountPayable;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="TAX_PAYABLE_ID")
    private GeneralLedger taxPayable;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ACCURED_SALARY_ID")
    private GeneralLedger accuredSalary;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="LOAN_ID")
    private GeneralLedger loan;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="OTHER_PAYABLE_ID")
    private GeneralLedger otherPayable;

//REVENUE
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="INCOME_ID")
    private GeneralLedger income;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="OTHER_INCOME_ID")
    private GeneralLedger otherIncome;

//COGS
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="COST_OF_SALE_ID")
    private GeneralLedger costOfSale;

//EXPENSE
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="GENERAL_EXPENSE_ID")
    private GeneralLedger generalExpense;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="DOCTOR_EXPENSE_ID")
    private GeneralLedger doctorExpense;


    public AccountConfig(){}


    public GeneralLedger getCash() {
        return cash;
    }

    public void setCash(GeneralLedger cash) {
        this.cash = cash;
    }

    public GeneralLedger getBank() {
        return bank;
    }

    public void setBank(GeneralLedger bank) {
        this.bank = bank;
    }

    public GeneralLedger getInventory() {
        return inventory;
    }

    public void setInventory(GeneralLedger inventory) {
        this.inventory = inventory;
    }

    public GeneralLedger getAccountReceivable() {
        return accountReceivable;
    }

    public void setAccountReceivable(GeneralLedger accountReceivable) {
        this.accountReceivable = accountReceivable;
    }

    public GeneralLedger getPalntEquipment() {
        return palntEquipment;
    }

    public void setPalntEquipment(GeneralLedger palntEquipment) {
        this.palntEquipment = palntEquipment;
    }

    public GeneralLedger getFurnitureFixture() {
        return furnitureFixture;
    }

    public void setFurnitureFixture(GeneralLedger furnitureFixture) {
        this.furnitureFixture = furnitureFixture;
    }

    public GeneralLedger getAccountPayable() {
        return accountPayable;
    }

    public void setAccountPayable(GeneralLedger accountPayable) {
        this.accountPayable = accountPayable;
    }

    public GeneralLedger getTaxPayable() {
        return taxPayable;
    }

    public void setTaxPayable(GeneralLedger taxPayable) {
        this.taxPayable = taxPayable;
    }

    public GeneralLedger getAccuredSalary() {
        return accuredSalary;
    }

    public void setAccuredSalary(GeneralLedger accuredSalary) {
        this.accuredSalary = accuredSalary;
    }

    public GeneralLedger getLoan() {
        return loan;
    }

    public void setLoan(GeneralLedger loan) {
        this.loan = loan;
    }

    public GeneralLedger getOtherPayable() {
        return otherPayable;
    }

    public void setOtherPayable(GeneralLedger otherPayable) {
        this.otherPayable = otherPayable;
    }

    public GeneralLedger getIncome() {
        return income;
    }

    public void setIncome(GeneralLedger income) {
        this.income = income;
    }

    public GeneralLedger getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(GeneralLedger otherIncome) {
        this.otherIncome = otherIncome;
    }

    public GeneralLedger getCostOfSale() {
        return costOfSale;
    }

    public void setCostOfSale(GeneralLedger costOfSale) {
        this.costOfSale = costOfSale;
    }

    public GeneralLedger getGeneralExpense() {
        return generalExpense;
    }

    public void setGeneralExpense(GeneralLedger generalExpense) {
        this.generalExpense = generalExpense;
    }

    public GeneralLedger getDoctorExpense() {
        return doctorExpense;
    }

    public void setDoctorExpense(GeneralLedger doctorExpense) {
        this.doctorExpense = doctorExpense;
    }
}
