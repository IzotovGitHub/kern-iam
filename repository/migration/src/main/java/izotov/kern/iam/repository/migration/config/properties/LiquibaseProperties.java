package izotov.kern.iam.repository.migration.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "spring.liquibase")
public class LiquibaseProperties {
    private boolean enabled;
    private String changeLog;
    private boolean dropFirst;
}
