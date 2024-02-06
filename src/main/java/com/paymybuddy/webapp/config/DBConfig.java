package com.paymybuddy.webapp.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*@Configuration
@EnableJpaRepositories(basePackages = 
  "com.paymybuddy.webapp.repository")
@EnableTransactionManagement*/
@Configuration
@EnableJpaRepositories(basePackages = "com.paymybuddy.webapp.repository")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class DBConfig  {

	 /*  @Bean
    @Profile("test")
DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://${MYSQL_HOST:localhost}:3306/paymybuddy_test?serverTimezone=Europe/Paris");
        dataSource.setUsername("buddyuser");
        dataSource.setPassword("buddy");

        return dataSource;
    }*/
    
    // configure entityManagerFactory
    // configure transactionManager
    // configure additional Hibernate properties
}