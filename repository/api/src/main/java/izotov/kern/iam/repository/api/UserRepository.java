package izotov.kern.iam.repository.api;

import com.querydsl.core.types.Expression;
import izotov.kern.iam.repository.api.record.KernUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    
    Mono<KernUser> create(KernUser user);
    
    Mono<KernUser> findByUsername(String username);
    
    Flux<KernUser> findUsers(Expression<?> expression, Number offset, Number size);
    
    Mono<Long> count();
}
