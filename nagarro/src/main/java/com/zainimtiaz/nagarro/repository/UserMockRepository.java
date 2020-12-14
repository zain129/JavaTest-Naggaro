/**
 * @author Zain I.
 * created on 13/12/2020
 **/

package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Slf4j
public class UserMockRepository {

    private PasswordEncoder passwordEncoder;

    public UserMockRepository() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Setting dummy data for testadmin and testUser
     *
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        User user = null;
        List roles = new ArrayList();
        if (username.trim().equals("testadmin")) {
            user = new User();
            user.setUsername(username);
            user.setPassword(this.passwordEncoder.encode("adminpassword"));
//            user.setPassword("{noop}adminpassword");
            log.debug("User account status: " + user.isEnabled());

            roles.add("ROLE_ADMIN");
            roles.add("ROLE_USER");
            user.setRoles(roles);
        } else if (username.trim().equals("testUser")) {
            user = new User();
            user.setUsername(username);
            user.setPassword(this.passwordEncoder.encode("userpassword"));
//            user.setPassword("{noop}userpassword");

            roles.add("ROLE_USER");
            user.setRoles(roles);
        }
        return user;
    }

    public List<User> findAll() {
        List<User> userList = new ArrayList<User>();
        userList.add(new User("testadmin",
//                "{noop}adminpassword",
                this.passwordEncoder.encode("adminpassword"), false,
                Arrays.asList("ROLE_USER", "ROLE_ADMIN")));

        userList.add(new User("testUser",
//                "{noop}userpassword",
                this.passwordEncoder.encode("userpassword"), false,
                Arrays.asList("ROLE_USER")));
        return userList;
    }
}
