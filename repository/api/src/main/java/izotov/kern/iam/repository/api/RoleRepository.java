package izotov.kern.iam.repository.api;

import izotov.kern.iam.repository.api.record.Role;
import reactor.core.publisher.Mono;

public interface RoleRepository {
    
    Mono<Role> findByName(String name);
}
