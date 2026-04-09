package izotov.kern.iam.repository.api;

import izotov.kern.iam.repository.api.record.Role;
import izotov.kern.iam.repository.api.record.UserRole;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface UserRoleRepository {
    
    Mono<Boolean> checkAccess(UUID userId, UUID roleId);
    
    Mono<UserRole> assign(UUID userId, Role role);
    
    Flux<UserRole> findUserRoles(UUID userId);
}
