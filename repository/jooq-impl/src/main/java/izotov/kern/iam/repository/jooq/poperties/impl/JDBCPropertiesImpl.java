package izotov.kern.iam.repository.jooq.poperties.impl;

import izotov.kern.iam.repository.jooq.poperties.JDBCProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "spring.datasource")
public class JDBCPropertiesImpl implements JDBCProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private Hikari hikari;
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
    
    public void setHikari(Hikari hikari) {
        this.hikari = hikari;
    }
    
    @Override
    public String getUrl() {
        return url;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getDriverClassName() {
        return driverClassName;
    }
    
    @Override
    public Hikari getHikari() {
        return hikari;
    }
    
    public static class HikariImpl implements Hikari {
        private Integer maximumPoolSize;
    
        public void setMaximumPoolSize(Integer maximumPoolSize) {
            this.maximumPoolSize = maximumPoolSize;
        }
    
        @Override
        public Integer getMaximumPoolSize() {
            return maximumPoolSize;
        }
    }
    
}
