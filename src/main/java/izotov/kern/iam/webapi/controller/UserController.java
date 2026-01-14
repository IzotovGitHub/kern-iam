package izotov.kern.iam.webapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import izotov.kern.iam.dao.service.UserService;
import izotov.kern.iam.webapi.dto.UserListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    
    @GetMapping("/kern/users")
    @Operation(summary = "Get user list")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user list")
    )
    public Mono<Page<UserListDto>> all(@RequestParam Integer page, @RequestParam Integer size) {
        log.debug("Request received: GET /kern/users with params: page={}, size={}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return userService.findPageableUsers(pageable)
                .map(UserListDto::from)
                .collectList()
                .zipWith(userService.count())
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
    }
}
