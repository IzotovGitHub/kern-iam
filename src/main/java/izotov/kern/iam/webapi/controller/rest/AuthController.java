package izotov.kern.iam.webapi.controller.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import izotov.kern.iam.webapi.dto.LoginUserDto;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Validated
@Tag(name = "auth")
@RestController
@RequestMapping("/kern/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final ReactiveAuthenticationManager authenticationManager;
    
    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, Object>>> login(@Valid @NonNull @RequestBody LoginUserDto userDto) {
        log.debug("try login: {}", userDto.username());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto.username(), userDto.password());
        return authenticationManager.authenticate(authentication)
                .map(auth -> {
                    log.debug("Authentication successful for user: {}", authentication.getName());
                    
                    Map<String, Object> response = new HashMap<>();
                    response.put("username", authentication.getName());
                    response.put("roles", authentication.getAuthorities());
                    response.put("authenticated", true);
                    return ResponseEntity.ok(Collections.unmodifiableMap(response));
                });
    }
    
}
