package com.zainimtiaz.nagarro.enums;

public enum InvoiceMessageEnum {

    DATA("DATA"),
    STATUS("STATUS"),

    ERROR("ERROR"),
    SUCCESS("SUCCESS"),
    INFO("INFO"),
    NOT_FOUND("NOT_FOUND"),
    ADMIN_LOGGEDIN_FAILED("ADM_ERR_01"),
    ADMIN_LOGGEDIN_SUCCESS("ADM_SUC_01"),
    ADMIN_LOGGEDIN_FETCHED_FAILED("ADM_ERR_03"),
    ADMIN_LOGGEDIN_FETCHED_SUCCESS("ADM_SUC_03"),
    ADMIN_DASHBOARD_FETCHED_FAILED("ADM_ERR_04"),
    ADMIN_DASHBOARD_FETCHED_SUCCESS("ADM_SUC_04"),
    ADMIN_NOT_FOUND("ADM_ERR_02"),
    ADMIN_ACCESS_GRANTED("ADM_AUTH_SUC_01"),
    EXCEPTION("SYS_ERR_01"),
    INSUFFICIENT_PARAMETERS("SYS_ERR_02"),
    USER_LOGGED_OUT_FAILED("USR_ERR_01"),
    ROLE_PERMISSION_FETCH_FAILED("ROL_PER_ERR_01"),
    ROLE_PERMISSION_FETCH_SUCCESS("ROL_PER_SUC_02"),
    ROLE_PERMISSION_ASSIGN_SUCCESS("ROL_PER_SUC_02"),
    ROLE_PERMISSION_ASSIGN_ERROR("ROL_PER_ERR_02"),;



    private String value;

    InvoiceMessageEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
