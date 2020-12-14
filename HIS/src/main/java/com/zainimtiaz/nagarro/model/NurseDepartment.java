package com.zainimtiaz.nagarro.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="NURSE_DEPARTMENT")
public class NurseDepartment extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NURSE_ID", nullable = false)
    private Nurse nurse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID", nullable = false)
    private Department department;

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
