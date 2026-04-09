package izotov.kern.iam.security.config;

import org.springframework.beans.factory.annotation.Value;


public interface DefaultUserConfig {
    @Value("${spring.application.admin.username}")
    void setUsername(String username);
    
    @Value("${spring.application.admin.password}")
    void setPassword(String password);
}
