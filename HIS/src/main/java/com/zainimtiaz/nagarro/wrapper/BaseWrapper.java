package com.zainimtiaz.nagarro.wrapper;

/**
 * Created by jamal on 8/20/2018.
 */
public class BaseWrapper {


    private Long id;

    private String createdOn;

    private String updatedOn;

    public BaseWrapper() {
    }

    public BaseWrapper(Long id, String createdOn, String updatedOn) {
        this.id = id;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }
}
