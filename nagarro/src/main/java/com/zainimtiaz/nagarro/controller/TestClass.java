/**
 * @author Zain I.
 * created on 15/12/2020
 **/

package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.config.db.SpringJdbcConfig;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestClass {

    public static void main(String[] args) {
        SpringJdbcConfig springJdbcConfig = new SpringJdbcConfig();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(springJdbcConfig.msAccessDataSource());

        String date = "09.08.2012";
        String sql = "SELECT TRY_CAST('?' as date)";
        String result = jdbcTemplate.queryForObject(sql,
                new Object[]{date}, String.class);
        System.out.println("Result: " + result);
    }
}
