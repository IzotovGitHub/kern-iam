package izotov.kern.iam.dao.repo.impl;

import izotov.kern.iam.dao.repo.RoleRepository;
import izotov.kern.iam.jooq.tables.pojos.Role;
import izotov.kern.iam.jooq.tables.records.RoleRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static izotov.kern.iam.jooq.tables.Role.ROLE;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    
    private final DSLContext dsl;
    
    private static final Function<RoleRecord, Role> ROLE_MAPPER = r -> r.into(izotov.kern.iam.jooq.tables.pojos.Role.class);
    
    @Override
    public Mono<Role> findByName(String name) {
        log.debug("findByName: {}", name);
        return Mono.from(select()
                .where(ROLE.NAME.eq(name))
                .limit(1))
                .map(ROLE_MAPPER);
    }
    
    private SelectWhereStep<RoleRecord> select() {
        return dsl.selectFrom(ROLE);
    }
}
