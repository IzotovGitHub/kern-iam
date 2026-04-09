package izotov.kern.iam.repository.api.record;

import java.util.UUID;

public record UserRole(
        UUID id,
        UUID userId,
        UUID roleId,
        String roleName) {
}
