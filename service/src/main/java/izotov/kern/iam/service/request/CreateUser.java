package izotov.kern.iam.service.request;


import izotov.kern.iam.validation.annotation.Password;
import jakarta.validation.constraints.NotBlank;

public record CreateUser(
        @NotBlank
        String username,
        @NotBlank
        @Password
        String password) {
}
