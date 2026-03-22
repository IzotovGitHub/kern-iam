package izotov.kern.iam.mapper;

import izotov.kern.iam.dao.entity.KernUserRecord;
import izotov.kern.iam.jooq.tables.pojos.Usr;
import izotov.kern.iam.webapi.dto.NewUserDto;
import izotov.kern.iam.webapi.dto.UserCreatedDto;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    
    public static KernUserRecord toRecord(Usr user) {
        return new KernUserRecord(user.getUuid(), user.getUsername(), user.getPassword());
    }
    
    public static Usr toPojo(NewUserDto newUserDto) {
        return new Usr(null, newUserDto.username(), newUserDto.password());
    }
    
    @Valid
    public static UserCreatedDto toUserCreated(KernUserRecord user) {
        return new UserCreatedDto(user.id());
    }
}
