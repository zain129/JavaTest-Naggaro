package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/*
 * @author    : Tahir Mehmood
 * @Date      : 18-Jul-2018
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.model
 * @FileName  : Nurse
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "MANAGER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Manager extends StaffProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="USER_ID", unique= true)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}