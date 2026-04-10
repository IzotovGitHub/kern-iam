package izotov.kern.iam.repository.jooq.config.impl;

import io.r2dbc.spi.ConnectionFactory;
import izotov.kern.iam.repository.jooq.config.DatasourceConfig;
import izotov.kern.iam.repository.jooq.poperties.JDBCProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

@Configuration
public class DatasourceConfigImpl implements DatasourceConfig {
    
    private JDBCProperties jdbcProperties;
    
    @Bean
    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
    
    /*@Bean
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
    }*/
    
    @Override
    public JDBCProperties getJdbcProperties() {
        return jdbcProperties;
    }
    
    @Override
    public void setJdbcProperties(JDBCProperties jdbcProperties) {
        this.jdbcProperties = jdbcProperties;
    }
}
