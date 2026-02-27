package izotov.kern.iam.config;

import izotov.kern.iam.builder.UserBuilder;
import izotov.kern.iam.dao.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DefaultUserConfig {

    @Value("${spring.application.admin.username}")
    private String username;
    
    @Value("${spring.application.admin.password}")
    private String password;
    
    @Bean
    public CommandLineRunner initAdmin(UserService userService) {
        return args -> userService.exists(username)
                .filter(exists -> !exists)
                .map(ignore -> UserBuilder.nw()
                        .setUsername(username)
                        .setPassword(password)
                        .build())
                .flatMap(userService::create)
                .subscribe(
                        result -> log.info("Admin initialization complete: {}", result.getUuid()),
                        error -> log.error("", error)
                );
    }
    
    @Data
    public static class Admin {
        @NotNull
        private String username;
        @NotNull
        private String password;
    }
}
