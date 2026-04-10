package izotov.kern.iam.repository.migration.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import izotov.kern.iam.repository.migration.config.properties.JDBCProperties;
import izotov.kern.iam.repository.migration.config.properties.LiquibaseProperties;
import liquibase.integration.spring.SpringLiquibase;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Setter
@Getter
@Configuration
public class LiquibaseConfig {

    private JDBCProperties jdbcProperties;

    private LiquibaseProperties liquibaseProperties;

    @Bean
    public DataSource jdbcDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcProperties.getUrl());
        config.setUsername(jdbcProperties.getUsername());
        config.setPassword(jdbcProperties.getPassword());
        config.setDriverClassName(jdbcProperties.getDriverClassName());
        config.setMaximumPoolSize(jdbcProperties.getHikari().getMaximumPoolSize());
        return DataSourceBuilder
                .derivedFrom(new HikariDataSource(config))
                .build();
    }

    @Bean
    public SpringLiquibase liquibase(@Qualifier("jdbcDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setShouldRun(liquibaseProperties.isEnabled());
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        return liquibase;
    }
}
