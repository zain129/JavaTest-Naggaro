package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.Department;

import java.util.List;

/*
 * @author    : Arif Heer
 * @Date      : 4/10/2018
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer    Date       Version  Operation  Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.wrapper
 * @FileName  : DepartmentWrapper
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class DepartmentWrapper {

    long id;
    String name;
    String description;
    boolean active;
    private boolean checkedDepartment;
    long value;
    String label;
    String branch;
    long branchId;
    long branchDepartmentId;
    List<Long> selectedBranches;
    List<BranchesListWrapper> listOfBranches;
   // List<BranchResponseWrapper> listOfBranches;
    /***
     * if true ,this object has child record
     * if false, this object has no child record
     * ***/
    private boolean hasChild;

    public DepartmentWrapper() {
    }

    public DepartmentWrapper(DepartmentWrapper departmentWrapper) {
        this.id = departmentWrapper.getId();
        this.name = departmentWrapper.getName();
        this.description = departmentWrapper.description;
        this.active = departmentWrapper.isActive();
        this.label =departmentWrapper.getName();
        this.value =departmentWrapper.getId();
    }

    public DepartmentWrapper(Department dpt) {
        this.id = dpt.getId();
        this.name = dpt.getName();
        this.description = dpt.getDescription();
        this.label =dpt.getName();
        this.value =dpt.getId();
        this.active = dpt.getStatus() == null ? false : dpt.getStatus();
        if (dpt.getDepartmentMedicalServices() != null && dpt.getDepartmentMedicalServices().size() > 0) {
            this.hasChild = true;
        } else {
            this.hasChild = false;
        }


    }

    public List<BranchesListWrapper> getListOfBranches() {
        return listOfBranches;
    }

    public void setListOfBranches(List<BranchesListWrapper> listOfBranches) {
        this.listOfBranches = listOfBranches;
    }

    public DepartmentWrapper(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.label = name;
        this.value = id;
    }


    public List<Long> getSelectedBranches() {
        return selectedBranches;
    }

    public void setSelectedBranches(List<Long> selectedBranches) {
        this.selectedBranches = selectedBranches;
    }

    public long getBranchDepartmentId() {
        return branchDepartmentId;
    }

    public void setBranchDepartmentId(long branchDepartmentId) {
        this.branchDepartmentId = branchDepartmentId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isCheckedDepartment() {
        return checkedDepartment;
    }

    public void setCheckedDepartment(boolean checkedDepartment) {
        this.checkedDepartment = checkedDepartment;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }
}