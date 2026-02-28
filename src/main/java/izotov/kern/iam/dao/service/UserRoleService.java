package izotov.kern.iam.dao.service;

import izotov.kern.iam.dao.entity.KernUserRecord;
import izotov.kern.iam.dao.entity.UserRoleRecord;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface UserRoleService {
    
    Mono<Void> assign(KernUserRecord user, UserRoleRecord role);
    
    Mono<Set<String>> findUserRoles(KernUserRecord user);
}
