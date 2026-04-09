package izotov.kern.iam.service.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Valid
@Getter
@Schema(description = "User item")
@RequiredArgsConstructor
public class PageableUser {
    
    @NotBlank
    @Schema(description = "username")
    private final String username;
}
