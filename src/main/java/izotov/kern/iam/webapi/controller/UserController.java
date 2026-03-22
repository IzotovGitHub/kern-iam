package izotov.kern.iam.webapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import izotov.kern.iam.dao.service.UserService;
import izotov.kern.iam.exception.handler.response.ErrorResponse;
import izotov.kern.iam.exception.handler.response.ValidationErrorResponse;
import izotov.kern.iam.webapi.dto.NewUserDto;
import izotov.kern.iam.webapi.dto.UserCreatedDto;
import izotov.kern.iam.webapi.dto.UserListDto;
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
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/kern/users")
    @Operation(summary = "Get user list")
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved user list",
                    content = @Content(schema = @Schema(implementation = Page.class))
            )
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
    
    @PostMapping("/kern/user/create")
    @Operation(summary = "Create new user")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User successfully created",
                    content = @Content(schema = @Schema(implementation = UserCreatedDto.class))
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
    public Mono<UserCreatedDto> create(@Validated @RequestBody NewUserDto newUser) {
        log.debug("Request received: POST /kern/user/create");
        return userService.newUser(newUser);
    }
}
