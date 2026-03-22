package izotov.kern.iam.webapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Valid
@Getter
@Schema(description = "User created")
@RequiredArgsConstructor
public class UserCreatedDto {
    
    @NotNull
    @Schema(description = "New user UUID")
    private final UUID id;
    
}
