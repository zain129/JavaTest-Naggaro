package com.zainimtiaz.nagarro.wrapper;

public class PrefixWrapper {

    private Long id;
    private String name;
    private String module;
    private Long startValue;
    private Long currentValue;

    public PrefixWrapper() {
    }

    public PrefixWrapper(Long id, String name, String module, Long startValue, Long currentValue) {
        this.id = id;
        this.name = name;
        this.module = module;
        this.startValue = startValue;
        this.currentValue = currentValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Long getStartValue() {
        return startValue;
    }

    public void setStartValue(Long startValue) {
        this.startValue = startValue;
    }

    public Long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }
}
