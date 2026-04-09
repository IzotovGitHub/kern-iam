package izotov.kern.iam.webapi.rbody;

import izotov.kern.iam.validation.annotation.Password;
import jakarta.validation.constraints.NotBlank;

public record AuthUserRequest (
        @NotBlank
        String username,
        @NotBlank
        @Password
        String password
){
}
