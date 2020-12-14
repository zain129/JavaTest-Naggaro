package com.zainimtiaz.nagarro;

import com.zainimtiaz.nagarro.model.ActiveUserList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NagarroApplication {

    public static void main(String[] args) {
        SpringApplication.run(NagarroApplication.class, args);
    }

    @Bean
    public ActiveUserList activeUserList() {
        return new ActiveUserList();
    }

}
