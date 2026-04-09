package izotov.kern.iam.repository.jooq.impl;

import com.querydsl.core.types.Expression;
import izotov.kern.iam.repository.api.UserRepository;
import izotov.kern.iam.repository.api.record.KernUser;
import izotov.kern.iam.repository.jooq.codegen.tables.records.UsrRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static izotov.kern.iam.repository.jooq.codegen.tables.Usr.USR;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext dsl;
    
    private static final Function<UsrRecord, KernUser> USR_MAPPER = r -> new KernUser(r.getUuid(), r.getUsername(), r.getPassword());
    
    @Override
    public Mono<KernUser> create(KernUser user) {
        log.debug("create: {}", user.username());
        return Mono.from(dsl.insertInto(USR)
                        .set(USR.USERNAME, user.username())
                        .set(USR.PASSWORD, user.password())
                        .returning())
                .map(USR_MAPPER);
    }
    
    @Override
    public Mono<KernUser> findByUsername(String username) {
        log.debug("findByUsername: {}", username);
        return Mono.from(select()
                        .where(USR.USERNAME.endsWithIgnoreCase(username))
                        .limit(1))
                .map(USR_MAPPER);
    }
    
    @Override
    public Flux<KernUser> findUsers(Expression<?> expression, Number offset, Number size) {
        log.debug("find {} users", size);
        return Flux.from(select()
                        //.where(condition)
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
