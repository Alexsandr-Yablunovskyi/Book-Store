package yablunovskyi.bookstore.service.impl;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yablunovskyi.bookstore.dto.user.UserRegistrationRequestDto;
import yablunovskyi.bookstore.dto.user.UserRegistrationResponseDto;
import yablunovskyi.bookstore.exception.RegistrationException;
import yablunovskyi.bookstore.mapper.UserMapper;
import yablunovskyi.bookstore.model.Role;
import yablunovskyi.bookstore.model.User;
import yablunovskyi.bookstore.repository.RoleRepository;
import yablunovskyi.bookstore.repository.UserRepository;
import yablunovskyi.bookstore.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    
    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.existsByEmail(request.email())) {
            throw new RegistrationException(("Unable to complete registration. "
                    + "User with email %s already exists").formatted(request.email()));
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Collections.singleton(roleRepository.findByName(Role.RoleName.USER)));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
