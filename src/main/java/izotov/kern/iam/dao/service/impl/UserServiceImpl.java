package izotov.kern.iam.dao.service.impl;

import izotov.kern.iam.dao.entity.KernUserRecord;
import izotov.kern.iam.dao.repo.UserRepository;
import izotov.kern.iam.dao.service.UserService;
import izotov.kern.iam.jooq.tables.pojos.Usr;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static izotov.kern.iam.jooq.tables.Usr.USR;
import static org.jooq.impl.DSL.noCondition;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {
    
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    
    @Override
    public Mono<Boolean> exists(String username) {
        return findUsers(USR.USERNAME.endsWithIgnoreCase(username), Pageable.ofSize(1))
                .collectList()
                .map(resp -> !resp.isEmpty());
    }
    
    @Override
    public Flux<KernUserRecord> findUsers(Condition condition, Pageable pageable) {
        return userRepository.findUsers(condition, pageable.getOffset(), pageable.getPageSize())
                .map(KernUserRecord::new);
    }
    
    @Override
    public Flux<KernUserRecord> findPageableUsers(Pageable pageable) {
        return findUsers(noCondition(), pageable);
    }
    
    @Override
    public Mono<KernUserRecord> create(Usr user) {
        final String pwd = user.getPassword();
        if(Objects.isNull(pwd)) {
            // TODO Добавить выброс осмысленного исключения
            return Mono.error(() -> new Exception(""));
        }
        
        String encodedPwd = encoder.encode(user.getPassword());
        user.setPassword(encodedPwd);
        return userRepository.create(user)
                .map(KernUserRecord::new);
    }
    
    @Override
    public Mono<Long> count() {
        return userRepository.count();
    }
    
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(usr -> User.builder()
                        .username(usr.getUsername())
                        .password(usr.getPassword())
                        .build());
    }
    
    
    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, @Nullable String newPassword) {
        return NOOP.updatePassword(user, newPassword);
    }
}
