package com.zainimtiaz.nagarro.model;

import javax.persistence.*;
import java.io.Serializable;

/*
 * @author    : Tahir Mehmood
 * @Date      : 20-Jul-2018
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
 * @Package   : com.sd.his.*
 * @FileName  : UserAuthAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "BRANCH_DOCTOR")
public class BranchDoctor extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRANCH_ID", nullable = false)
    private Branch branch;

    @Column(name = "PRIMARY_BRANCH", columnDefinition = "boolean default true")
    private Boolean primaryBranch;


    @Override
    public String toString() {
        return "BranchDoctor{" +
                "doctor=" + doctor +
                ", branch=" + branch +
                ", primaryBranch=" + primaryBranch +
                '}';
    }

    public BranchDoctor() {
    }

    public BranchDoctor(Doctor doctor, Branch branch, Boolean primaryBranch) {
        this.doctor = doctor;
        this.branch = branch;
        this.primaryBranch = primaryBranch;

    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Boolean getPrimaryBranch() {
        return primaryBranch;
    }

    public void setPrimaryBranch(Boolean primaryBranch) {
        this.primaryBranch = primaryBranch;
    }


}