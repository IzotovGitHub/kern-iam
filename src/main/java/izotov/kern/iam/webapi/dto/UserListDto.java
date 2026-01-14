package izotov.kern.iam.webapi.dto;

import izotov.kern.iam.jooq.tables.pojos.Usr;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserListDto {
    
    private final String username;
    
    public static UserListDto from(Usr user) {
        return new UserListDto(user.getUsername());
    }
}
