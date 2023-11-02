package yablunovskyi.bookstore.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import yablunovskyi.bookstore.dto.user.UserRegistrationRequestDto;
import yablunovskyi.bookstore.dto.user.UserRegistrationResponseDto;
import yablunovskyi.bookstore.model.User;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {
    UserRegistrationResponseDto toDto(User user);
    
    User toUser(UserRegistrationRequestDto request);
}
