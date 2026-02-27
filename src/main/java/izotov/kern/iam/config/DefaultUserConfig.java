package izotov.kern.iam.config;

import izotov.kern.iam.dao.entity.UserRoleRecord;
import izotov.kern.iam.dao.service.RoleService;
import izotov.kern.iam.dao.service.UserRoleService;
import izotov.kern.iam.jooq.tables.builder.UserBuilder;
import izotov.kern.iam.dao.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static izotov.kern.iam.dao.entity.UserRoleRecord.ERole.ADMIN;

@Slf4j
@Configuration
public class DefaultUserConfig {

    @Value("${spring.application.admin.username}")
    private String username;
    
    @Value("${spring.application.admin.password}")
    private String password;
    
    @Bean
    public CommandLineRunner initAdmin(UserService userService,
                                       RoleService roleService,
                                       UserRoleService userRoleService) {
        return args -> userService.exists(username)
                .filter(exists -> !exists)
                .map(ignore -> UserBuilder.nw()
                        .setUsername(username)
                        .setPassword(password)
                        .build())
                .flatMap(userService::create)
                .flatMap(admin -> roleService.find(ADMIN)
                        .flatMap(role -> userRoleService.assign(admin, role))
                        .thenReturn(admin))
                .subscribe(
                        result -> log.info("Admin initialization complete: {}", result.getId()),
                        error -> log.error("Unexpected error", error)
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
