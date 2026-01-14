package izotov.kern.iam.dao.repo.impl;

import izotov.kern.iam.dao.repo.UserRepository;
import izotov.kern.iam.jooq.tables.pojos.Usr;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static izotov.kern.iam.jooq.tables.Usr.USR;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext dsl;
    
    @Override
    public Flux<Usr> findUsers(Condition condition, Number offset, Number size) {
        log.debug("find {} users", size);
        return Flux.from(dsl.selectFrom(USR)
                .where(condition)
                .limit(offset, size))
                .map(record -> record.into(Usr.class));
    }
    
    @Override
    public Mono<Long> count() {
        log.debug("user count");
        return Mono.from(dsl.select(DSL.count())
                .from(USR))
                .map(record -> record.value1().longValue());
    }
}
