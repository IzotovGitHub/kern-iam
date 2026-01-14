package izotov.kern.iam.dao.service.impl;

import izotov.kern.iam.dao.repo.UserRepository;
import izotov.kern.iam.dao.service.UserService;
import izotov.kern.iam.jooq.tables.pojos.Usr;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.jooq.impl.DSL.noCondition;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    @Override
    public Flux<Usr> findUsers(Condition condition, Pageable pageable) {
        return userRepository.findUsers(condition, pageable.getOffset(), pageable.getPageSize());
    }
    
    @Override
    public Flux<Usr> findPageableUsers(Pageable pageable) {
        return findUsers(noCondition(), pageable);
    }
    
    @Override
    public Mono<Long> count() {
        return userRepository.count();
    }
}
