package com.zainimtiaz.config;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurationCustomZI extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/getEmp").hasAnyRole("USER", "ADMIN")
			.antMatchers("/getEmp/**").hasAnyRole("USER", "ADMIN")
			.antMatchers("/saveEmp").hasAnyRole("USER", "ADMIN")
			.antMatchers("/delete/**").hasAnyRole("ADMIN")
			
			.and().exceptionHandling().accessDeniedPage("/403")
//	        .and().formLogin().loginPage("/login").usernameParameter("userName").passwordParameter("password")
            .and().formLogin().loginPage("/").permitAll()    
            	.successHandler(new AuthSucessHandlerConfiguration())
            	.failureUrl("/login/loginFailed")
//			.anyRequest().authenticated()
			.and().logout().invalidateHttpSession(true).permitAll();
		
		httpSecurity.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
			.maximumSessions(1)
			.expiredSessionStrategy(sies -> {
				sies.getRequest().getContextPath();
				sies.getResponse().sendRedirect(sies.getRequest().getContextPath()+"loginExpired");
			})
			.expiredUrl("/loginExpired");
		
		httpSecurity.csrf().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		// testuser and testadmin in memory auth
		authenticationManagerBuilder.inMemoryAuthentication()
			.withUser("testadmin").password("adminpassword").authorities("ROLE_USER", "ROLE_ADMIN")
			.and().withUser("testUser").password("userpassword").authorities("ROLE_USER");
	}
	
}