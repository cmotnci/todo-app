package com.todo.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfiguration {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean
    public HikariDataSource dataSource() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dataSourceProperties.getJdbcUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setConnectionTimeout(dataSourceProperties.getConnectionTimeout());
        dataSource.setMaximumPoolSize(dataSourceProperties.getMaxPoolSize());
        dataSource.setPoolName(dataSourceProperties.getPoolName());

        return dataSource;
    }
}
