package izotov.kern.iam.service.impl;

import izotov.kern.iam.repository.api.RoleRepository;
import izotov.kern.iam.repository.api.record.Role;
import izotov.kern.iam.service.api.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    
    private final RoleRepository roleRepository;
    
    @Override
    public Mono<Role> findByName(String role) {
        return roleRepository.findByName(role)
                .switchIfEmpty(Mono.error(new Exception(""))); // TODO Добавить выброс осмысленного исключения
    }
}
