package com.ing.ContactsApp.controller.mapper;

import com.ing.ContactsApp.controller.dto.UserRequestDto;
import com.ing.ContactsApp.controller.dto.UserResponseDto;
import com.ing.ContactsApp.domain.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import static java.util.Objects.nonNull;
@Component
public class UserMapper {

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User mapToEntity(UserRequestDto userDto) {
        var user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setUserRole(userDto.getUserRole());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }

    public User editUser(User user, UserRequestDto userDto) {
        if ((nonNull(userDto.getEmail()) && !userDto.getEmail().isEmpty())) {
            user.setEmail(userDto.getEmail());
        }
        if ((nonNull(userDto.getPassword()) && !userDto.getPassword().isEmpty())) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        if ((nonNull(userDto.getUserRole()) && !userDto.getUserRole().name().isEmpty())) {
            user.setUserRole(userDto.getUserRole());
        }
        if ((nonNull(userDto.getFullName()) && !userDto.getFullName().isEmpty())) {
            user.setFullName(userDto.getFullName());
        }
        return user;
    }

    public static UserResponseDto mapToDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setFullName(user.getFullName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setUserRole(user.getUserRole().name());
        return userResponseDto;
    }


}
