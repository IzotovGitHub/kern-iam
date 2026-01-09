package izotov.kern.iam.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.r2dbc.spi.ConnectionFactory;
import izotov.kern.iam.config.poperties.JDBCProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DatasourceConfig {
    
    private final JDBCProperties jdbcProperties;
    
    @Bean
    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
    
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
}
