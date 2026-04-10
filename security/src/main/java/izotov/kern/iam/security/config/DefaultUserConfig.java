package izotov.kern.iam.security.config;

import izotov.kern.iam.excaption.UserAlreadyExistsException;
import izotov.kern.iam.service.api.UserRoleService;
import izotov.kern.iam.service.api.UserService;
import izotov.kern.iam.service.request.CreateUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DefaultUserConfig {


    @Value("${spring.application.admin.username:kernadmin}")
    private String username;

    @Value("${spring.application.admin.password:changeit}")
    private String password;

    @Bean
    public CommandLineRunner initAdmin(UserService userService,
                                       UserRoleService userRoleService) {
        CreateUser user = new CreateUser(username, password);
        return args -> userService.newUser(user)
                .onErrorComplete(UserAlreadyExistsException.class)
                .flatMap(createdUser -> userRoleService.assign(createdUser.getId(), "ADMIN")
                        .thenReturn(createdUser))
                .subscribe(
                        result -> log.debug("Admin initialization complete: {}", result.getId()),
                        error -> log.error("Unexpected error", error)
                );
    }

}
