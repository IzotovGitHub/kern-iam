package izotov.kern.iam.dao.service.impl;

import izotov.kern.iam.dao.entity.KernUserRecord;
import izotov.kern.iam.dao.repo.UserRepository;
import izotov.kern.iam.dao.service.UserRoleService;
import izotov.kern.iam.dao.service.UserService;
import izotov.kern.iam.exception.PasswordRequiredException;
import izotov.kern.iam.exception.UserAlreadyExistsException;
import izotov.kern.iam.exception.UserNotFoundException;
import izotov.kern.iam.jooq.tables.pojos.Usr;
import izotov.kern.iam.mapper.UserMapper;
import izotov.kern.iam.webapi.dto.NewUserDto;
import izotov.kern.iam.webapi.dto.UserCreatedDto;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
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

import static org.jooq.impl.DSL.noCondition;

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
                .onErrorReturn(UserNotFoundException.class, false);
    }
    
    @Override
    public Flux<KernUserRecord> findUsers(Condition condition, Pageable pageable) {
        return userRepository.findUsers(condition, pageable.getOffset(), pageable.getPageSize())
                .map(UserMapper::toRecord);
    }
    
    @Override
    public Mono<KernUserRecord> findUser(Condition condition) {
        return userRepository.findUsers(condition, 0, 1)
                .next()
                .switchIfEmpty(Mono.error(new UserNotFoundException(condition)))
                .map(UserMapper::toRecord);
    }
    
    @Override
    public Flux<KernUserRecord> findPageableUsers(Pageable pageable) {
        return findUsers(noCondition(), pageable);
    }
    
    @Override
    public Mono<UserCreatedDto> newUser(NewUserDto user) {
        return exists(user.username())
                .flatMap(exists -> exists
                        ? Mono.error(() -> new UserAlreadyExistsException(user))
                        : Mono.empty())
                .thenReturn(UserMapper.toPojo(user))
                .flatMap(this::create)
                .map(UserMapper::toUserCreated);
    }
    
    private Mono<KernUserRecord> create(Usr user) {
        final String pwd = user.getPassword();
        
        if (Objects.isNull(pwd)) {
            return Mono.error(() -> new PasswordRequiredException("Password cannot be null"));
        }
        
        String encodedPwd = encoder.encode(user.getPassword());
        user.setPassword(encodedPwd);
        return userRepository.create(user)
                .map(UserMapper::toRecord);
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
                    KernUserRecord user = tuple.getT1();
                    Set<String> roles = tuple.getT2();
                    return User.builder()
                            .username(user.username())
                            .password(user.password())
                            .passwordEncoder(encoder::encode)
                            .roles(roles.toArray(new String[0]))
                            .disabled(false)
                            .build();
                });
    }
    
    private Mono<KernUserRecord> findByUserName(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UserNotFoundException(username)))
                .map(UserMapper::toRecord);
    }
    
    
    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, @Nullable String newPassword) {
        return NOOP.updatePassword(user, newPassword);
    }
}
