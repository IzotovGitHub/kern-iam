package izotov.kern.iam.repository.migration.properties.impl;

import izotov.kern.iam.repository.migration.properties.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.liquibase")
public class LiquibasePropertiesImpl implements LiquibaseProperties {
    private boolean enabled;
    private String changeLog;
    private boolean dropFirst;
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public String getChangeLog() {
        return changeLog;
    }
    
    @Override
    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }
    
    @Override
    public boolean isDropFirst() {
        return dropFirst;
    }
    
    @Override
    public void setDropFirst(boolean dropFirst) {
        this.dropFirst = dropFirst;
    }
}
