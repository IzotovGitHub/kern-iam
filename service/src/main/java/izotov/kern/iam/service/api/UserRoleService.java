package izotov.kern.iam.service.api;

import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

public interface UserRoleService {

    Mono<Void> assign(UUID userId, String role);

    Mono<Set<String>> findUserRoles(UUID userId);
}
