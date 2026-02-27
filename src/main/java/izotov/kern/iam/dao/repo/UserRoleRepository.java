package izotov.kern.iam.dao.repo;

import izotov.kern.iam.jooq.tables.pojos.UsrRole;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface UserRoleRepository {
    
    Mono<Boolean> checkAccess(UUID userId, UUID roleId);
    
    Mono<UsrRole> assign(UUID userId, UUID roleId);
}
