package izotov.kern.iam.config;

import io.r2dbc.spi.ConnectionFactory;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfig {
    @Bean
    public DSLContext dslContext(ConnectionFactory connectionFactory) {
        return DSL.using(connectionFactory);
    }
}
