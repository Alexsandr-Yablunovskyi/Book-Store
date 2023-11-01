package yablunovskyi.bookstore.service;

import yablunovskyi.bookstore.dto.user.UserRegistrationRequestDto;
import yablunovskyi.bookstore.dto.user.UserResponseDto;
import yablunovskyi.bookstore.exception.RegistrationException;

public interface UserService {
    /*UserResponseDto findByEmail(String email);*/
    
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
}
