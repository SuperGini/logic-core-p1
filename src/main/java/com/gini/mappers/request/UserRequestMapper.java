package com.gini.mappers.request;

import com.gini.dto.request.UserRequest;
import com.gini.dto.response.UserResponse;
import com.gini.mappers.Mapper;
import com.gini.model.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRequestMapper implements Mapper<User, UserRequest, UserResponse> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User mapFromRequest(UserRequest userRequest) {
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
                user.getId()
        );
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
