package izotov.kern.iam.repository.jooq.config.impl;

import io.r2dbc.spi.ConnectionFactory;
import izotov.kern.iam.repository.jooq.config.JooqConfig;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class JooqConfigImpl implements JooqConfig {
    @Bean
    public DSLContext dslContext(ConnectionFactory connectionFactory) {
        return DSL.using(connectionFactory);
    }
}
