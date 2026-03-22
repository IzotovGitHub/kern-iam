package izotov.kern.iam.config;

import izotov.kern.iam.dao.service.RoleService;
import izotov.kern.iam.dao.service.UserRoleService;
import izotov.kern.iam.dao.service.UserService;
import izotov.kern.iam.dao.entity.builder.UserBuilder;
import izotov.kern.iam.exception.UserAlreadyExistsException;
import izotov.kern.iam.webapi.dto.NewUserDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

import static izotov.kern.iam.jooq.tables.Usr.USR;

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
        NewUserDto user = new NewUserDto(username, password);
        return args -> userService.newUser(user)
                .onErrorComplete(UserAlreadyExistsException.class)
                .map(created -> USR.UUID.eq(created.getId()))
                .flatMap(userService::findUser)
                .flatMap(admin -> roleService.findByName("ADMIN")
                        .flatMap(role -> userRoleService.assign(admin, role))
                        .thenReturn(admin))
                .subscribe(
                        result -> log.debug("Admin initialization complete: {}", result.id()),
                        error -> log.error("Unexpected error", error)
                );
    }
}
