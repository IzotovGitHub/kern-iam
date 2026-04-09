package izotov.kern.iam.service.api;

import izotov.kern.iam.repository.api.record.KernUser;
import izotov.kern.iam.repository.api.record.Role;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface UserRoleService {
    
    Mono<Void> assign(KernUser user, Role role);
    
    Mono<Set<String>> findUserRoles(KernUser user);
}
