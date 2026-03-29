package izotov.kern.iam.webapi.controller.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class LoginController {
    
    @GetMapping("/login")
    public Mono<String> getLoginPage() {
        return Mono.just("login");
    }
    
}
