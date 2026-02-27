package izotov.kern.iam.dao.service;

import izotov.kern.iam.jooq.tables.pojos.Usr;
import org.jooq.Condition;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    
    Mono<Boolean> exists(String username);
    
    Flux<Usr> findUsers(Condition condition, Pageable pageable);
    
    Flux<Usr> findPageableUsers(Pageable pageable);
    
    Mono<Usr> create(Usr user);
    
    Mono<Long> count();
}
