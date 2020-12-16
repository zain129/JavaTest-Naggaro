/**
 * @author Zain I.
 * created on 13/12/2020
 **/

package com.zainimtiaz.nagarro.config;

import com.zainimtiaz.nagarro.entity.model.ActiveUserList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
public class LoggedInUser implements HttpSessionBindingListener {

    private String username;
    private ActiveUserList activeUserList;

    public LoggedInUser(String username, ActiveUserList activeUserList) {
        this.username = username;
        this.activeUserList = activeUserList;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        List<String> users = activeUserList.getUsers();
        LoggedInUser loggedInUser = (LoggedInUser) event.getValue();
        if (!users.contains(loggedInUser.getUsername())) {
            users.add(loggedInUser.getUsername());
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        List<String> users = activeUserList.getUsers();
        LoggedInUser loggedInUser = (LoggedInUser) event.getValue();
        if (users.contains(loggedInUser.getUsername())) {
            users.remove(loggedInUser.getUsername());
        }
    }
}
