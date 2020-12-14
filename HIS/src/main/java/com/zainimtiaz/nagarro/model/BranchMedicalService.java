package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/*
 * @author    : Irfan Nasim
 * @Date      : 14-May-18
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
 * @FileName  : BranchMedicalService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "BRANCH_MEDICAL_SERVICE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchMedicalService extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRANCH_ID")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEDICAL_SERVICE_ID")
    private MedicalService medicalService;

    public BranchMedicalService() {
    }

    public BranchMedicalService(Branch branch, MedicalService medicalService) {
        this.branch = branch;
        this.medicalService = medicalService;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public MedicalService getMedicalService() {
        return medicalService;
    }

    public void setMedicalService(MedicalService medicalService) {
        this.medicalService = medicalService;
    }
}
