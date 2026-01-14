package izotov.kern.iam.dao.repo;

import izotov.kern.iam.jooq.tables.pojos.Usr;
import org.jooq.Condition;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    
    Flux<Usr> findUsers(Condition condition, Number offset, Number size);
    
    Mono<Long> count();
}
