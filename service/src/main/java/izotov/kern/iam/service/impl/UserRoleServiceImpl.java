package izotov.kern.iam.service.impl;

import izotov.kern.iam.repository.api.UserRoleRepository;
import izotov.kern.iam.repository.api.record.KernUser;
import izotov.kern.iam.repository.api.record.Role;
import izotov.kern.iam.repository.api.record.UserRole;
import izotov.kern.iam.service.api.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    
    private final UserRoleRepository userRoleRepository;
    
    @Override
    public Mono<Void> assign(KernUser user, Role role) {
        UUID userId = user.id();
        UUID roleId = role.id();
        return userRoleRepository.checkAccess(userId, roleId)
                .doOnNext(hasAccess -> {
                    if (hasAccess) {
                        log.warn("User {} already has access {}", user.username(), role.name());
                    }
                })
                .filter(hasAccess -> !hasAccess)
                .flatMap(ignore -> userRoleRepository.assign(userId, role))
                .then();
    }
    
    @Override
    public Mono<Set<String>> findUserRoles(KernUser user) {
        return userRoleRepository.findUserRoles(user.id())
                .map(UserRole::roleName)
                .collect(Collectors.toSet());
    }
}
