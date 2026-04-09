package izotov.kern.iam.service.impl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import izotov.kern.iam.repository.api.UserRepository;
import izotov.kern.iam.repository.api.record.KernUser;
import izotov.kern.iam.service.api.UserRoleService;
import izotov.kern.iam.service.api.UserService;
import izotov.kern.iam.service.request.CreateUser;
import izotov.kern.iam.service.response.PageableUser;
import izotov.kern.iam.service.response.UserCreated;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {
    
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    
    private final UserRoleService userRoleService;
    
    @Override
    public Mono<Boolean> exists(String username) {
        return findByUserName(username)
                .thenReturn(true)
                .onErrorReturn(Exception.class, false); // UserNotFoundException
    }
    
    @Override
    public Flux<KernUser> findUsers(Expression<?> expression, Pageable pageable) {
        return userRepository.findUsers(expression, pageable.getOffset(), pageable.getPageSize());
    }
    
    @Override
    public Mono<KernUser> findById(UUID uuid) {
        return userRepository.findUsers(Expressions.asBoolean(true), 0, 1)
                .next()
                .switchIfEmpty(Mono.error(new Exception()));
    }
    
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Flux<PageableUser> findPageableUsers(Pageable pageable) {
        return findUsers(Expressions.asBoolean(true), pageable)
                .map(user -> new PageableUser(user.username()));
    }
    
    @Override
    public Mono<UserCreated> newUser(CreateUser user) {
        return exists(user.username())
                .flatMap(exists -> exists
                        ? Mono.error(() -> new Exception(""))
                        : Mono.empty())
                .then(create(user))
                .map(created -> new UserCreated(created.id()));
    }
    
    private Mono<KernUser> create(CreateUser cu) {
        final String pwd = cu.password();
        
        if (Objects.isNull(pwd)) {
            return Mono.error(() -> new Exception("Password cannot be null"));
        }
        
        String encodedPwd = encoder.encode(pwd);
        KernUser user = new KernUser(null, cu.username(), encodedPwd);
        return userRepository.create(user);
    }
    
    @Override
    public Mono<Long> count() {
        return userRepository.count();
    }
    
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return findByUserName(username)
                .zipWhen(userRoleService::findUserRoles)
                .map(tuple -> {
                    KernUser user = tuple.getT1();
                    Set<String> roles = tuple.getT2();
                    return User.builder()
                            .username(user.username())
                            .password(user.password())
                            .roles(roles.toArray(new String[0]))
                            .disabled(false)
                            .build();
                });
    }
    
    private Mono<KernUser> findByUserName(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new Exception(username)));
    }
    
    
    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, @Nullable String newPassword) {
        return NOOP.updatePassword(user, newPassword);
    }
}
