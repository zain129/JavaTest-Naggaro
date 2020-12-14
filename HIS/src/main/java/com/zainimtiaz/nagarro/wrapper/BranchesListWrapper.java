package com.zainimtiaz.nagarro.wrapper;

public class BranchesListWrapper {

    private String name;
    private String label;
    private long value;
    private long id ;
    public BranchesListWrapper(){}

    public BranchesListWrapper(String name, long id,String label, long value) {
        this.name = name;
        this.label = label;
        this.value = value;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
