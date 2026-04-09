package izotov.kern.iam.repository.api.record;

import java.util.UUID;

public record KernUser(
        UUID id,
        String username,
        String password) {
}
