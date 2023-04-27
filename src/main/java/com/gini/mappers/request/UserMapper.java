package com.gini.mappers.request;

import com.gini.dto.request.user.CreateUserRequest;
import com.gini.dto.response.user.UserResponse;
import com.gini.mappers.Mapper;
import com.gini.persitence.model.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, CreateUserRequest, UserResponse> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User mapFromRequest(CreateUserRequest userRequest) {
        return User.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .password(encodePassword(userRequest.password()))
                .build();
    }

    @Override
    public UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getUsername(),
                user.getEmail(),
                user.getId().toString()
        );
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
