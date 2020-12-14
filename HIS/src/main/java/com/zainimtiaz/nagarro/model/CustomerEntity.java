package com.zainimtiaz.nagarro.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "customer")
public class CustomerEntity implements Serializable {

    @Id

    @Column (name = "customer_id",unique = true,nullable = false, updatable = false)
    private long customerId;

    @Column (name = "customer_firstname")
    private String firstName;

    @Column (name = "customer_lastname")
    private String lastName;

    @Column (name= "customer_language" )
    private String customer_language;

    @Column(name = "customer_password")
    private String password;

    public CustomerEntity() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomer_language() {
        return customer_language;
    }

    public void setCustomer_language(String customer_language) {
        this.customer_language = customer_language;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }







}
