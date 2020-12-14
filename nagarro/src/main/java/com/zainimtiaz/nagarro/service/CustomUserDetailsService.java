/**
 * @author Zain I.
 * created on 13/12/2020
 **/

package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.entity.User;
import com.zainimtiaz.nagarro.repository.UserMockRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private UserMockRepository users;

    public CustomUserDetailsService(UserMockRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.users.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }
        return user;
    }
}
