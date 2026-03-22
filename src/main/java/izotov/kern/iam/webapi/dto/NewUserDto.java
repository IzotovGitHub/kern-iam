package izotov.kern.iam.webapi.dto;

import jakarta.validation.constraints.NotBlank;


public record NewUserDto(
        @NotBlank String username,
        @NotBlank String password) {
}
