/**
 * @author Zain I.
 * created on 13/12/2020
 **/

package com.zainimtiaz.nagarro.entity.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ActiveUserList {

    public List<String> users;

    public ActiveUserList() {
        this.users = new ArrayList<String>();
    }

    public ActiveUserList(List<String> users) {
        this.users = users;
    }
}
