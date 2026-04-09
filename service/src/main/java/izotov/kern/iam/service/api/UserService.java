package izotov.kern.iam.service.api;

import com.querydsl.core.types.Expression;
import izotov.kern.iam.repository.api.record.KernUser;
import izotov.kern.iam.service.request.CreateUser;
import izotov.kern.iam.service.response.PageableUser;
import izotov.kern.iam.service.response.UserCreated;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.jooq.Condition;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {
    
    Mono<Boolean> exists(String username);
    
    Flux<KernUser> findUsers(Expression<?> expression, Pageable pageable);
    
    Mono<KernUser> findById(UUID uuid);
    
    Flux<PageableUser> findPageableUsers(Pageable pageable);
    
    Mono<UserCreated> newUser(@Valid @NonNull CreateUser user);
    
    Mono<Long> count();
}
