package com.gini.services;

import com.gini.dto.request.user.LoginUserRequest;
import com.gini.dto.request.user.CreateUserRequest;
import com.gini.dto.response.UserResponse;
import com.gini.error.error.InvalidCredentialsException;
import com.gini.error.error.UserAlreadyExists;
import com.gini.error.error.UserNotFoundException;
import com.gini.mappers.request.UserMapper;
import com.gini.persitence.model.entities.User;
import com.gini.persitence.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gini.constants.LogicCoreConstants.EMAIL_PATTERN;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final UserMapper userRequestMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserResponse createUser(CreateUserRequest userRequest) {

        userRepository.findUserByUsernameOrEmail(userRequest.username(), userRequest.email())
                .ifPresent(user -> {
                    throw new UserAlreadyExists("user already exists");
                });


        var user = userRequestMapper.mapFromRequest(userRequest);

        var savedUser = userRepository.persist(user);

        return userRequestMapper.mapToResponse(savedUser);

    }

    @Transactional(readOnly = true)
    public UserResponse loginUser(LoginUserRequest userRequest) {

        var matcher = EMAIL_PATTERN.matcher(userRequest.usernameOrEmail());

        User user;

        if (matcher.matches()) {
            user = userRepository.findUserByEmail(userRequest.usernameOrEmail())
                    .orElseThrow(() -> new UserNotFoundException("User not Found"));

            if (isCorrectPassword(userRequest, user)) {
                return userRequestMapper.mapToResponse(user);
            }
        }

        user = userRepository.findUserByUsername(userRequest.usernameOrEmail())
                .orElseThrow(() -> new UserNotFoundException("User not Found"));

        if (isCorrectPassword(userRequest, user)) {
            return userRequestMapper.mapToResponse(user);
        }

        throw new InvalidCredentialsException("Invalid credentials");
    }

    private boolean isCorrectPassword(LoginUserRequest userRequest, User user) {
        return passwordEncoder
                .matches(userRequest.password(), user.getPassword());
    }
}
