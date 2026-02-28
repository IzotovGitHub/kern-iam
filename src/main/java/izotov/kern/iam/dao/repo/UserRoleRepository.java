package izotov.kern.iam.dao.repo;

import izotov.kern.iam.dao.entity.UserRoleRecord;
import izotov.kern.iam.jooq.tables.pojos.UsrRole;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface UserRoleRepository {
    
    Mono<Boolean> checkAccess(UUID userId, UUID roleId);
    
    Mono<UsrRole> assign(UUID userId, UserRoleRecord role);
    
    Flux<UsrRole> findUserRoles(UUID userId);
}
