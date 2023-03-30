package com.gini.services;

import com.gini.dto.request.UserRequest;
import com.gini.model.entities.User;
import com.gini.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public User createUser(UserRequest userRequest){

        //todo: find user in db -> if he exists -> thow UserAllreadyexistsException

        User user = User.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .password(userRequest.password())
                .build();

        return userRepository.persist(user);

    }




}
