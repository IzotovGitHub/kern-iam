package izotov.kern.iam.dao.service.impl;

import izotov.kern.iam.dao.entity.KernUserRecord;
import izotov.kern.iam.dao.entity.UserRoleRecord;
import izotov.kern.iam.dao.repo.UserRoleRepository;
import izotov.kern.iam.dao.service.UserRoleService;
import izotov.kern.iam.jooq.tables.pojos.UsrRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    
    private final UserRoleRepository userRoleRepository;
    
    @Override
    public Mono<Void> assign(KernUserRecord user, UserRoleRecord role) {
        UUID userId = user.getId();
        UUID roleId = role.getId();
        return userRoleRepository.checkAccess(userId, roleId)
                .doOnNext(hasAccess -> {
                    if(hasAccess) {
                        log.warn("User {} already has access {}", user.getUserName(), role.name());
                    }
                })
                .filter(hasAccess -> !hasAccess)
                .flatMap(ignore -> userRoleRepository.assign(userId, role))
                .then();
    }
    
    @Override
    public Mono<Set<String>> findUserRoles(KernUserRecord user) {
        return userRoleRepository.findUserRoles(user.getId())
                .map(UsrRole::getRoleName)
                .collect(Collectors.toSet());
    }
}
