package izotov.kern.iam.repository.jooq.impl;

import izotov.kern.iam.repository.api.RoleRepository;
import izotov.kern.iam.repository.api.record.Role;
import izotov.kern.iam.repository.jooq.codegen.tables.records.RoleRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static izotov.kern.iam.repository.jooq.codegen.Tables.ROLE;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    
    private final DSLContext dsl;
    
    private static final Function<RoleRecord, Role> ROLE_MAPPER = r -> new Role(r.getUuid(), r.getName());
    
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
