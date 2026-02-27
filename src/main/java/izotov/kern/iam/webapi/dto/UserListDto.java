package izotov.kern.iam.webapi.dto;

import izotov.kern.iam.dao.entity.KernUserRecord;
import izotov.kern.iam.jooq.tables.pojos.Usr;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserListDto {
    
    private final String username;
    
    public static UserListDto from(KernUserRecord user) {
        return new UserListDto(user.getUserName());
    }
}
