package izotov.kern.iam.config;

import izotov.kern.iam.config.poperties.LiquibaseConfigProperties;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class LiquibaseConfig {
    
    private final LiquibaseConfigProperties liquibaseConfigProperties;
    
    @Bean
    public SpringLiquibase liquibase(@Qualifier("jdbcDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setShouldRun(liquibaseConfigProperties.isEnabled());
        liquibase.setChangeLog(liquibaseConfigProperties.getChangeLog());
        liquibase.setDropFirst(liquibaseConfigProperties.isDropFirst());
        return liquibase;
    }
}
