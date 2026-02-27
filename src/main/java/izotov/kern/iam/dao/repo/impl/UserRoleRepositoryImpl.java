package izotov.kern.iam.dao.repo.impl;

import izotov.kern.iam.dao.repo.UserRoleRepository;
import izotov.kern.iam.jooq.tables.pojos.UsrRole;
import izotov.kern.iam.jooq.tables.records.UsrRoleRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

import static izotov.kern.iam.jooq.tables.UsrRole.USR_ROLE;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRoleRepositoryImpl implements UserRoleRepository {
    
    private final DSLContext dsl;
    
    private static final Function<UsrRoleRecord, UsrRole> USR_ROLE_MAPPER = r -> r.into(UsrRole.class);
    
    @Override
    public Mono<Boolean> checkAccess(UUID userId, UUID roleId) {
        log.debug("checkAccess: {} to {}", userId, roleId);
        return Mono.from(dsl.select(
                DSL.exists(
                        DSL.selectFrom(USR_ROLE)
                                .where(USR_ROLE.USR_ID.eq(userId))
                                .and(USR_ROLE.ROLE_ID.eq(roleId)))))
                .map(Record1::value1);
    }
    
    @Override
    public Mono<UsrRole> assign(UUID userId, UUID roleId) {
        log.debug("assign: {} to {}", userId, roleId);
        return Mono.from(dsl.insertInto(USR_ROLE)
                .set(USR_ROLE.USR_ID, userId)
                .set(USR_ROLE.ROLE_ID, roleId)
                .returning())
                .map(USR_ROLE_MAPPER);
    }
}
