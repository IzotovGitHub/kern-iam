package izotov.kern.iam.repository.migration.config.impl;

import izotov.kern.iam.repository.migration.config.LiquibaseConfig;
import izotov.kern.iam.repository.migration.properties.LiquibaseProperties;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfigImpl implements LiquibaseConfig {
    
    private LiquibaseProperties liquibaseProperties;
    
    @Bean
    public SpringLiquibase liquibase(@Qualifier("jdbcDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setShouldRun(liquibaseProperties.isEnabled());
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        return liquibase;
    }
    
    @Override
    public LiquibaseProperties getLiquibaseProperties() {
        return liquibaseProperties;
    }
    
    @Override
    public void setLiquibaseProperties(LiquibaseProperties liquibaseProperties) {
        this.liquibaseProperties = liquibaseProperties;
    }
}
