package izotov.kern.iam.repository.migration.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class JDBCProperties {

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private Hikari hikari;


    @Setter
    @Getter
    public static class Hikari {

        private Integer maximumPoolSize;
    }

}
