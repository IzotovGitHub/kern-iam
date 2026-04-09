package izotov.kern.iam.security.config.impl;

import izotov.kern.iam.exception.UserAlreadyExistsException;
import izotov.kern.iam.security.config.DefaultUserConfig;
import izotov.kern.iam.service.api.RoleService;
import izotov.kern.iam.service.api.UserRoleService;
import izotov.kern.iam.service.api.UserService;
import izotov.kern.iam.service.request.CreateUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
public class DefaultUserConfigImpl implements DefaultUserConfig {
    
    
    private String username;
    
    private String password;
    
    @Bean
    public CommandLineRunner initAdmin(UserService userService,
                                       RoleService roleService,
                                       UserRoleService userRoleService) {
        CreateUser user = new CreateUser(username, password);
        return args -> userService.newUser(user)
                .onErrorComplete(UserAlreadyExistsException.class)
                .flatMap(created -> userService.findById(created.getId()))
                .flatMap(admin -> roleService.findByName("ADMIN")
                        .flatMap(role -> userRoleService.assign(admin, role))
                        .thenReturn(admin))
                .subscribe(
                        result -> log.debug("Admin initialization complete: {}", result.id()),
                        error -> log.error("Unexpected error", error)
                );
    }
    
    @Override
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
