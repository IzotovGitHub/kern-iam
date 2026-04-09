package izotov.kern.iam.webapi.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import izotov.kern.iam.exception.handler.response.ErrorResponse;
import izotov.kern.iam.exception.handler.response.ValidationErrorResponse;
import izotov.kern.iam.service.api.UserService;
import izotov.kern.iam.service.request.CreateUser;
import izotov.kern.iam.service.response.PageableUser;
import izotov.kern.iam.service.response.UserCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@Validated
@RestController
@Tag(name = "user")
@RequestMapping("/kern/v1")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/users")
    @Operation(summary = "Get user list")
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved user list",
                    content = @Content(schema = @Schema(implementation = Page.class))
            )
    )
    public Mono<Page<PageableUser>> all(@RequestParam Integer page, @RequestParam Integer size) {
        log.debug("Request received: GET /kern/v1/users with params: page={}, size={}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return userService.findPageableUsers(pageable)
                .collectList()
                .zipWith(userService.count())
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
    }
    
    @PostMapping("/user/create")
    @Operation(summary = "Create new user")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User successfully created",
                    content = @Content(schema = @Schema(implementation = UserCreated.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserCreated> create(@Validated @RequestBody CreateUser newUser) {
        log.debug("Request received: POST /kern/v1/user/create");
        return userService.newUser(newUser);
    }
}
