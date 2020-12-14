package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.Branch;

/*
 * @author    : Qari Muhammad Jamal
 * @Date      : 17-Apr-18
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
 * @FileName  : BranchWrapper
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class BranchWrapperPart {

    long id;
    String name;
    private boolean checkedBranch;

    public BranchWrapperPart() {
    }

    public BranchWrapperPart(Branch branch) {
        this.id = branch.getId();
        this.name = branch.getName();
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

    public boolean isCheckedBranch() {
        return checkedBranch;
    }

    public void setCheckedBranch(boolean checkedBranch) {
        this.checkedBranch = checkedBranch;
    }
}