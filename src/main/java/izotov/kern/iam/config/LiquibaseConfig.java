package izotov.kern.iam.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import izotov.kern.iam.config.poperties.JDBCDatasourceProperties;
import izotov.kern.iam.config.poperties.LiquibaseConfigProperties;
import liquibase.integration.spring.SpringLiquibase;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class LiquibaseConfig {
    
    private final LiquibaseConfigProperties liquibaseConfigProperties;
    private final JDBCDatasourceProperties jdbcDatasourceProperties;
    
    @Bean
    public DataSource jdbcDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcDatasourceProperties.getUrl());
        config.setUsername(jdbcDatasourceProperties.getUsername());
        config.setPassword(jdbcDatasourceProperties.getPassword());
        config.setDriverClassName(jdbcDatasourceProperties.getDriverClassName());
        config.setMaximumPoolSize(jdbcDatasourceProperties.getHikari().getMaximumPoolSize());
        return DataSourceBuilder
                .derivedFrom(new HikariDataSource(config))
                .build();
    }
    
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
