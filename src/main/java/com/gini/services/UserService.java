package com.gini.services;

import com.gini.dto.request.UserLoginRequest;
import com.gini.dto.request.UserRequest;
import com.gini.dto.response.UserResponse;
import com.gini.error.error.UserAlreadyExists;
import com.gini.error.error.UserNotFoundException;
import com.gini.mappers.request.UserRequestMapper;
import com.gini.model.entities.User;
import com.gini.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

import static com.gini.constants.LogicCoreConstants.EMAIL_PATTERN;

@Service
@RequiredArgsConstructor
public class UserService {



    private final UserRepository userRepository;
    private final UserRequestMapper userRequestMapper;


    @Transactional
    public UserResponse createUser(UserRequest userRequest) {

        userRepository.findUserByUsernameOrEmail(userRequest.username(), userRequest.email())
                .ifPresent(user -> {
                    throw new UserAlreadyExists("user already exists");
                });


        var user = userRequestMapper.mapFromRequest(userRequest);

        var savedUser = userRepository.persist(user);

        return userRequestMapper.mapToResponse(savedUser);

    }

    @Transactional(readOnly = true)
    public UserResponse loginUser(UserLoginRequest userRequest){

        var matcher = EMAIL_PATTERN.matcher(userRequest.usernameOrEmail());

        User user;

        if(matcher.matches()){
            user = userRepository.findUserByEmail(userRequest.usernameOrEmail())
                                    .orElseThrow(() -> new UserNotFoundException("User not Found"));

            return userRequestMapper.mapToResponse(user);
        }

        user =  userRepository.findUserByUsername(userRequest.usernameOrEmail())
                                .orElseThrow(() -> new UserNotFoundException("User not Found"));

        return userRequestMapper.mapToResponse(user);
    }
}
