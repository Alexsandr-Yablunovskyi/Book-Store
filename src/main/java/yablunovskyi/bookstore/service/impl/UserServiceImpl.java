package yablunovskyi.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yablunovskyi.bookstore.dto.user.UserRegistrationRequest;
import yablunovskyi.bookstore.dto.user.UserResponseDto;
import yablunovskyi.bookstore.exception.RegistrationException;
import yablunovskyi.bookstore.mapper.UserMapper;
import yablunovskyi.bookstore.model.User;
import yablunovskyi.bookstore.repository.UserRepository;
import yablunovskyi.bookstore.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserResponseDto findByEmail(String email) {
        return userMapper.toDto(userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException(
                "Can't find user by email: %s".formatted(email)
        )));
    }
    
    @Override
    public UserResponseDto register(UserRegistrationRequest request) throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException(("Unable to complete registration. "
                    + "User with email %s already exists").formatted(request.getEmail()));
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
