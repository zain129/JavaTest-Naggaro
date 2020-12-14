/**
 * @author Zain I.
 * created on 13/12/2020
 **/

package com.zainimtiaz.nagarro.config;

import com.zainimtiaz.nagarro.config.jwt.JwtConfigurer;
import com.zainimtiaz.nagarro.config.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .antMatchers(HttpMethod.GET, "/auth/thisUser").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/statement/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/statement/date/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/statement/amount/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/statement/all/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/accounts/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));

        httpSecurity
                .sessionManagement()
                .maximumSessions(1).sessionRegistry(sessionRegistry());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("testUser").password("{noop}userpassword").roles("USER")
                .and()
                .withUser("testadmin").password("{noop}adminpassword").roles("ADMIN");
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
