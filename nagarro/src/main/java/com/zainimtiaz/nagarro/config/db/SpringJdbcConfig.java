/**
 * @author Zain I.
 * created on 14/12/2020
 **/

package com.zainimtiaz.nagarro.config.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@ComponentScan("com.zainimtiaz.nagarro")
@Configuration
@Slf4j
public class SpringJdbcConfig {
    @Autowired
    private Environment env;

    @Bean
    public DataSource msAccessDataSource() {
        String datasourceUrl = env.getProperty("spring.datasource.url");
        String datasourceDriver = env.getProperty("spring.datasource.driver");
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        log.info("Datasource: URL -> " + datasourceUrl + ", DriverClass -> " + datasourceDriver);
        driverManagerDataSource.setDriverClassName(datasourceDriver);
        driverManagerDataSource.setUrl(datasourceUrl);

        return driverManagerDataSource;
    }
}
