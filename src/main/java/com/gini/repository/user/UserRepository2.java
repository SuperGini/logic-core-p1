package com.gini.repository.user;

import com.gini.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository2 {

    Optional<User> findUserByUsernameOrEmail(String username, String email);
}
