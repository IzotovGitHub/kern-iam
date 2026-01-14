package izotov.kern.iam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig  {
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Отключаем CSRF для примера
                .authorizeExchange(exchanges -> exchanges
                        // Разрешаем доступ к определенным путям
                        .pathMatchers(
                                "/actuator/health",
                                "/kern/users"
                        ).permitAll()
                        // Требуем авторизацию для всех остальных
                        .anyExchange()
                        .authenticated()
                )
                .build();
    }
}
