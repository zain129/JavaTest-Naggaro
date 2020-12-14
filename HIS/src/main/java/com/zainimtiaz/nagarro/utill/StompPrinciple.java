package com.zainimtiaz.nagarro.utill;

import java.security.Principal;

public class StompPrinciple implements Principal {
    String name;

    StompPrinciple(String name) {
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }
}
