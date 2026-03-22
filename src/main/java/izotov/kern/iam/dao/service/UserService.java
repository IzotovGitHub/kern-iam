package izotov.kern.iam.dao.service;

import izotov.kern.iam.dao.entity.KernUserRecord;
import izotov.kern.iam.webapi.dto.NewUserDto;
import izotov.kern.iam.webapi.dto.UserCreatedDto;
import org.jooq.Condition;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    
    Mono<Boolean> exists(String username);
    
    Flux<KernUserRecord> findUsers(Condition condition, Pageable pageable);
    
    Mono<KernUserRecord> findUser(Condition condition);
    
    Flux<KernUserRecord> findPageableUsers(Pageable pageable);
    
    Mono<UserCreatedDto> newUser(NewUserDto user);
    
    Mono<Long> count();
}
