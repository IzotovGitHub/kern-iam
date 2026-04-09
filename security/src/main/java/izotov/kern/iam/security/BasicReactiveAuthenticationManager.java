package izotov.kern.iam.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class BasicReactiveAuthenticationManager implements ReactiveAuthenticationManager {
    
    private final PasswordEncoder encoder;
    private final ReactiveUserDetailsService userDetailsService;
    
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        String rawPwd = authentication.getCredentials().toString();
        return userDetailsService.findByUsername(username)
                .filter(user -> encoder.matches(rawPwd, user.getPassword()))
                .map(user -> new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
    }
}
