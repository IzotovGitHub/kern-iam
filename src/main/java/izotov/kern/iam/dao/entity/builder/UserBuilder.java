package izotov.kern.iam.dao.entity.builder;

import izotov.kern.iam.jooq.tables.pojos.Usr;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserBuilder {
    
    private final Usr user;
    
    public static UserBuilder nw(){
        return new UserBuilder(new Usr());
    }
    
    public UserBuilder setUsername(String username) {
        user.setUsername(username);
        return this;
    }
    
    public UserBuilder setPassword(String password) {
        user.setPassword(password);
        return this;
    }
    
    public Usr build() {
        return user;
    }
}
