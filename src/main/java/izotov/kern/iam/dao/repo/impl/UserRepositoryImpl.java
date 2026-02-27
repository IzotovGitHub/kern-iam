package izotov.kern.iam.dao.repo.impl;

import izotov.kern.iam.dao.repo.UserRepository;
import izotov.kern.iam.jooq.tables.pojos.Usr;
import izotov.kern.iam.jooq.tables.records.UsrRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static izotov.kern.iam.jooq.tables.Usr.USR;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext dsl;
    
    private static final Function<UsrRecord, Usr> USR_MAPPER = r -> r.into(Usr.class);
    
    @Override
    public Mono<Usr> create(Usr user) {
        log.debug("create: {}", user.getUsername());
        return Mono.from(dsl.insertInto(USR)
                .set(USR.USERNAME, user.getUsername())
                .set(USR.PASSWORD, user.getPassword())
                .returning())
                .map(USR_MAPPER);
    }
    
    @Override
    public Mono<Usr> findByUsername(String username) {
        log.debug("findByUsername: {}", username);
        return Mono.from(select()
                        .where(USR.USERNAME.endsWithIgnoreCase(username))
                        .limit(1))
                .map(USR_MAPPER);
    }
    
    @Override
    public Flux<Usr> findUsers(Condition condition, Number offset, Number size) {
        log.debug("find {} users", size);
        return Flux.from(select()
                        .where(condition)
                        .limit(offset, size))
                .map(USR_MAPPER);
    }
    
    @Override
    public Mono<Long> count() {
        log.debug("user count");
        return Mono.from(dsl.select(DSL.count())
                        .from(USR))
                .map(record -> record.value1().longValue());
    }
    
    private SelectWhereStep<UsrRecord> select() {
        return dsl.selectFrom(USR);
    }
}
