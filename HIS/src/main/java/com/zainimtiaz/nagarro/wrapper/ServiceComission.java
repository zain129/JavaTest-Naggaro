package com.zainimtiaz.nagarro.wrapper;

public class ServiceComission {

    private long id;
    private boolean checked ;
    private Double comission;

    public ServiceComission(long id, boolean checked, Double comission) {
        this.id = id;
        this.checked = checked;
        this.comission = comission;
    }

    public ServiceComission() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Double getComission() {
        return comission;
    }

    public void setComission(Double comission) {
        this.comission = comission;
    }
}
