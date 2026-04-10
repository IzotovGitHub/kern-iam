package izotov.kern.iam.service.impl;

import izotov.kern.iam.repository.api.RoleRepository;
import izotov.kern.iam.repository.api.UserRoleRepository;
import izotov.kern.iam.repository.api.record.UserRole;
import izotov.kern.iam.service.api.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public Mono<Void> assign(UUID userId, String roleName) {
        return roleRepository.findByName(roleName)
                .switchIfEmpty(Mono.error(new Exception("role not found")))
                .zipWhen(role -> userRoleRepository.checkAccess(userId, role.id()))
                .filter(pair -> {
                    if (pair.getT2()) {
                        log.warn("User {} already has access {}", userId, roleName);
                        return false;
                    }
                    return true;
                })
                .map(Tuple2::getT1)
                .flatMap(role -> userRoleRepository.assign(userId, role))
                .then();
    }

    @Override
    public Mono<Set<String>> findUserRoles(UUID userId) {
        return userRoleRepository.findUserRoles(userId)
                .map(UserRole::roleName)
                .collect(Collectors.toSet());
    }
}
