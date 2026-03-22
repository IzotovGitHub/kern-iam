package izotov.kern.iam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static izotov.kern.iam.security.ApiPathsConstants.OPEN_API;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Отключаем CSRF для примера
                .authorizeExchange(exchanges -> exchanges
                        // Разрешаем доступ к определенным путям
                        .pathMatchers(OPEN_API.paths()).permitAll()
                        // .pathMatchers(AUTHENTICATED_API.paths()).authenticated()
                        // Требуем авторизацию для всех остальных
                        .anyExchange()
                        .authenticated()
                )
                .build();
    }
}
