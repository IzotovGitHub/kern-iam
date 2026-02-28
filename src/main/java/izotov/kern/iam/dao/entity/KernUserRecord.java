package izotov.kern.iam.dao.entity;

import izotov.kern.iam.jooq.tables.pojos.Usr;

import java.util.UUID;

public record KernUserRecord(Usr user) {
    
    public UUID getId() {
        return user.getUuid();
    }
    
    public String getUserName() {
        return user.getUsername();
    }
    public String getPassword() {
        return user.getPassword();
    }
}
