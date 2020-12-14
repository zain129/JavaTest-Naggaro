package com.zainimtiaz.nagarro;

import com.zainimtiaz.nagarro.enums.UserTypeEnum;
import com.zainimtiaz.nagarro.model.Permission;
import com.zainimtiaz.nagarro.model.Role;
import com.zainimtiaz.nagarro.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zain Imtiaz
 */
@SpringBootApplication
@EnableJpaAuditing
public class HisApplication {
    private final Logger logger = LoggerFactory.getLogger(HisApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HisApplication.class, args);
    }


    @EventListener
    @Transactional(rollbackOn = Throwable.class)
    public void onBootStartup(ApplicationContextEvent event) {

        List<Permission> permissions = new ArrayList<>();
        permissions.add(new Permission("Appointment", "Appointment", "/dashboard/appointment/manage", true));
        permissions.add(new Permission("Associate Code Version", "Associate Code Version", "/dashboard/setting/codeVersion", true));
        permissions.add(new Permission("Branch", "Branch", "/dashboard/setting/branch", true));

        List<Role> roles = new ArrayList<>();
        roles.add(new Role(UserTypeEnum.ADMIN.name(), "adminUser role", true));
        roles.add(new Role(UserTypeEnum.TEST.name(), "testUser role", true));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        List<User> users = new ArrayList<>();
        users.add(new User("testadmin", UserTypeEnum.ADMIN.name(), encoder.encode("adminpassword"), true));
        users.add(new User("testUser", UserTypeEnum.TEST.name(), encoder.encode("userpassword"), true));
    }
}

