package izotov.kern.iam.dao.service;

import izotov.kern.iam.jooq.tables.pojos.Usr;
import org.jooq.Condition;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    
    Flux<Usr> findUsers(Condition condition, Pageable pageable);
    
    Flux<Usr> findPageableUsers(Pageable pageable);
    
    Mono<Long> count();
}
