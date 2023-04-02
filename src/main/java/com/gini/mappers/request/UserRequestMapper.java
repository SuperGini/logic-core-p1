package com.gini.mappers.request;

import com.gini.dto.request.UserRequest;
import com.gini.dto.response.UserResponse;
import com.gini.mappers.Mapper;
import com.gini.model.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper implements Mapper<User, UserRequest, UserResponse> {

    @Override
    public User mapFromRequest(UserRequest userRequest) {
        return User.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .password(userRequest.password())
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
}
