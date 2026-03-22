package izotov.kern.iam.webapi.dto;

import izotov.kern.iam.validation.annotation.Password;
import jakarta.validation.constraints.NotBlank;

public record NewUserDto(
        @NotBlank
        String username,
        @NotBlank
        @Password
        String password) {
}
