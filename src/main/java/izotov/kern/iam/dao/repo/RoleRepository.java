package izotov.kern.iam.dao.repo;

import izotov.kern.iam.jooq.tables.pojos.Role;
import reactor.core.publisher.Mono;

public interface RoleRepository {
    
    Mono<Role> findByName(String name);
}
