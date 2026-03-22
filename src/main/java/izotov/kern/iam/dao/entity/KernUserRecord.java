package izotov.kern.iam.dao.entity;

import java.util.UUID;

public record KernUserRecord(
        UUID id,
        String username,
        String password) {
}
