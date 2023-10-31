package yablunovskyi.bookstore.service;

import yablunovskyi.bookstore.dto.user.UserRegistrationRequest;
import yablunovskyi.bookstore.dto.user.UserResponseDto;
import yablunovskyi.bookstore.exception.RegistrationException;

public interface UserService {
    UserResponseDto findByEmail(String email);
    
    UserResponseDto register(UserRegistrationRequest request) throws RegistrationException;
}
