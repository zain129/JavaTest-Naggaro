/**
 * @author Zain Imtiaz
 **/

package com.zain.imtiaz.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.zain.imtiaz.java.repository")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class JavaApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaApplication.class, args);
	}
}
