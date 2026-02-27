package izotov.kern.iam.dao.entity;

import izotov.kern.iam.jooq.tables.pojos.Role;

import java.util.UUID;

public record UserRoleRecord(Role role) {
    
    public UUID getId(){
        return role.getUuid();
    }
    
    public String name() {
        return role.getName();
    }
    
    public enum ERole {
        ADMIN,
        AUTHORIZED,
        ANONYMOUS
    }
}


