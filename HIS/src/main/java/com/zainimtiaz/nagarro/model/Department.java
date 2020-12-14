package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zainimtiaz.nagarro.wrapper.DepartmentWrapper;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 24-Apr-18
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
 * @FileName  : ClinicalDepartment
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Entity
@Table(name = "DEPARTMENT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Department extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "NAME")
    private String name;

    @NaturalId
    @Column(name = "DEPT_ID", unique = true, nullable = false, updatable = false)
    private String deptId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS", columnDefinition = "boolean default true", nullable = false)
    private Boolean status;

    @JsonIgnore
    @OneToMany(targetEntity = BranchDepartment.class, mappedBy = "department")
    private List<BranchDepartment> branchDepartments;

    @JsonIgnore
    @OneToMany(targetEntity = DepartmentMedicalService.class, mappedBy = "department")
    private List<DepartmentMedicalService> departmentMedicalServices;

    @JsonIgnore
    @OneToMany(targetEntity = Doctor.class, mappedBy = "department")
    private List<Doctor> doctors;

    public Department() {
    }

    public Department(DepartmentWrapper createRequest) {
        this.name = createRequest.getName();
        this.description = createRequest.getDescription();
        this.status = createRequest.isActive();
    }

    public Department(String name, String deptId, String description) {
        this.name = name;
        this.deptId = deptId;
        this.description = description;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BranchDepartment> getBranchDepartments() {
        return branchDepartments;
    }

    public void setBranchDepartments(List<BranchDepartment> branchDepartments) {
        this.branchDepartments = branchDepartments;
    }

    public List<DepartmentMedicalService> getDepartmentMedicalServices() {
        return departmentMedicalServices;
    }

    public void setDepartmentMedicalServices(List<DepartmentMedicalService> departmentMedicalServices) {
        this.departmentMedicalServices = departmentMedicalServices;
    }
}
