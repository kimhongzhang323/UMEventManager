package com.umevent.management.infrastructure.config;

import com.umevent.management.infrastructure.config.properties.DbConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource, DbConfigProperties dbConfigProperties) {
        log.info("Database config loaded: url={}, user={}, hikari.maxPoolSize={}",
            safe(dbConfigProperties.getUrl()),
            safe(dbConfigProperties.getUsername()),
            dbConfigProperties.getHikari().getMaximumPoolSize());
        return new JdbcTemplate(dataSource);
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "<not-set>" : value;
    }
}
