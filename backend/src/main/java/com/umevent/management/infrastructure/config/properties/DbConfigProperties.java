package com.umevent.management.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource")
public class DbConfigProperties {

    private String url;
    private String username;
    private String password;
    private final Hikari hikari = new Hikari();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Hikari getHikari() {
        return hikari;
    }

    public static class Hikari {
        private Integer maximumPoolSize;
        private Integer minimumIdle;
        private Long idleTimeout;

        public Integer getMaximumPoolSize() {
            return maximumPoolSize;
        }

        public void setMaximumPoolSize(Integer maximumPoolSize) {
            this.maximumPoolSize = maximumPoolSize;
        }

        public Integer getMinimumIdle() {
            return minimumIdle;
        }

        public void setMinimumIdle(Integer minimumIdle) {
            this.minimumIdle = minimumIdle;
        }

        public Long getIdleTimeout() {
            return idleTimeout;
        }

        public void setIdleTimeout(Long idleTimeout) {
            this.idleTimeout = idleTimeout;
        }
    }
}
