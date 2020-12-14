package com.zainimtiaz.nagarro.wrapper.response;

public class StaffWrapper {

    Long uId;
    Long pId;
    String firstName;
    String lastName;
    String email;
    String primaryBranch;
    String username;
    String userType;
    String label;
    Long value;

    public StaffWrapper(Long uId, Long pId, String username, String userType , String firstName, String lastName, String email, String primaryBranch) {
        this.uId = uId;
        this.pId = pId;
        this.username=username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.primaryBranch = primaryBranch;
        this.userType=userType;
        this.label = username;
        this.value =uId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimaryBranch() {
        return primaryBranch;
    }

    public void setPrimaryBranch(String primaryBranch) {
        this.primaryBranch = primaryBranch;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
