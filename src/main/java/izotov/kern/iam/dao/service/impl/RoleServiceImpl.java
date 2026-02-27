package izotov.kern.iam.dao.service.impl;

import izotov.kern.iam.dao.entity.UserRoleRecord;
import izotov.kern.iam.dao.repo.RoleRepository;
import izotov.kern.iam.dao.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    
    private final RoleRepository roleRepository;
    
    @Override
    public Mono<UserRoleRecord> find(UserRoleRecord.ERole role) {
        return roleRepository.findByName(role.name())
                .switchIfEmpty(Mono.error(new Exception(""))) // TODO Добавить выброс осмысленного исключения
                .map(UserRoleRecord::new);
    }
}
