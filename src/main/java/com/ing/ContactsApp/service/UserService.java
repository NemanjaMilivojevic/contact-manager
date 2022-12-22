package com.ing.ContactsApp.service;

import com.ing.ContactsApp.domain.entity.User;
import com.ing.ContactsApp.domain.repository.UserRepository;
import com.ing.ContactsApp.service.exception.AlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new AlreadyExistsException("User already exists");
        userRepository.save(user);
    }

    public void updateUser(User updatedUser, String oldEmail) {
        if (!oldEmail.equals(updatedUser.getEmail())) {
            if (userRepository.existsByEmail(updatedUser.getEmail())) {
                throw new AlreadyExistsException("Email already exists.");
            }
        }
        userRepository.save(updatedUser);
    }

    public User findById(Long userId) {
        return userRepository.getUserById(userId);
    }

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
