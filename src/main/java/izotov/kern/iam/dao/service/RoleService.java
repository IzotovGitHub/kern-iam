package izotov.kern.iam.dao.service;

import izotov.kern.iam.dao.entity.UserRoleRecord;
import izotov.kern.iam.dao.entity.UserRoleRecord.ERole;
import reactor.core.publisher.Mono;

public interface RoleService {
    
    Mono<UserRoleRecord> find(ERole role);
}
