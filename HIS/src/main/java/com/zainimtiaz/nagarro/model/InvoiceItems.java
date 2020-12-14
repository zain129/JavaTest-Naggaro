package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "INVOICE_ITEMS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceItems  extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Column(name = "CODE")
    private String code;

    @Column(name = "SERVICE_NAME")
    private String serviceName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "QUANTITY", columnDefinition = "int default '0'")
    private Integer quantity;

    @Column(name = "UNIT_FEE", columnDefinition = "double default '0.00'")
    private Double unitFee;

    @Column(name = "TAX_RATE", columnDefinition = "double default '0.00'")
    private Double taxRate;

    @Column(name = "DISCOUNT_RATE", columnDefinition = "double default '0.00'")
    private Double discountRate;

    @Column(name = "COMMISSION_RATE", columnDefinition = "double default '0.00'")
    private Double commissionRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_ID", nullable = false)
    private Invoice invoice;



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitFee() {
        return unitFee;
    }

    public void setUnitFee(Double unitFee) {
        this.unitFee = unitFee;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Double commissionRate) {
        this.commissionRate = commissionRate;
    }
}
