package izotov.kern.iam.dao.service;

import izotov.kern.iam.dao.entity.UserRoleRecord;
import reactor.core.publisher.Mono;

public interface RoleService {
    
    Mono<UserRoleRecord> findByName(String role);
}
