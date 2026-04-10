package izotov.kern.iam.service.api;

import izotov.kern.iam.service.request.CreateUser;
import izotov.kern.iam.service.response.PageableUser;
import izotov.kern.iam.service.response.UserCreated;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<Boolean> exists(String username);

    Flux<PageableUser> findPageableUsers(Pageable pageable);

    Mono<UserCreated> newUser(@Valid @NonNull CreateUser user);

    Mono<Long> count();
}
