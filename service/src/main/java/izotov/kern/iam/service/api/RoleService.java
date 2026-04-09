package izotov.kern.iam.service.api;

import izotov.kern.iam.repository.api.record.Role;
import reactor.core.publisher.Mono;

public interface RoleService {
    
    Mono<Role> findByName(String role);
}
