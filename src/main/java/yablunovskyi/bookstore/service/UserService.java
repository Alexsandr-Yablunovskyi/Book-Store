package yablunovskyi.bookstore.service;

import yablunovskyi.bookstore.dto.user.UserRegistrationRequestDto;
import yablunovskyi.bookstore.dto.user.UserRegistrationResponseDto;
import yablunovskyi.bookstore.exception.RegistrationException;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException;
}
