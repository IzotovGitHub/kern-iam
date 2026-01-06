package izotov.kern.iam.config.poperties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "spring.datasource")
public class JDBCDatasourceProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private Hikari hikari = new Hikari();
    
    @Setter
    @Getter
    public static class Hikari {
        private Integer maximumPoolSize;
    }
    
}
