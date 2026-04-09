package izotov.kern.iam.repository.jooq.impl;

import izotov.kern.iam.repository.api.UserRoleRepository;
import izotov.kern.iam.repository.api.record.Role;
import izotov.kern.iam.repository.api.record.UserRole;
import izotov.kern.iam.repository.jooq.codegen.tables.records.UsrRoleRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

import static izotov.kern.iam.repository.jooq.codegen.Tables.USR_ROLE;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRoleRepositoryImpl implements UserRoleRepository {
    
    private final DSLContext dsl;
    
    private static final Function<UsrRoleRecord, UserRole> USR_ROLE_MAPPER = r -> new UserRole(r.getUuid(), r.getUsrId(), r.getRoleId(), r.getRoleName());
    
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
    public Mono<UserRole> assign(UUID userId, Role role) {
        log.debug("assign: {} to {}", userId, role.name());
        return Mono.from(dsl.insertInto(USR_ROLE)
                        .set(USR_ROLE.USR_ID, userId)
                        .set(USR_ROLE.ROLE_ID, role.id())
                        .set(USR_ROLE.ROLE_NAME, role.name())
                        .returning())
                .map(USR_ROLE_MAPPER);
    }
    
    @Override
    public Flux<UserRole> findUserRoles(UUID userId) {
        log.debug("findUserRoles: {} ", userId);
        return Flux.from(select()
                        .where(USR_ROLE.USR_ID.eq(userId)))
                .map(USR_ROLE_MAPPER);
    }
    
    private SelectWhereStep<UsrRoleRecord> select() {
        return dsl.selectFrom(USR_ROLE);
    }
}
