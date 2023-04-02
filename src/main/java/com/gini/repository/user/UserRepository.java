package com.gini.repository.user;

import com.gini.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(propagation = Propagation.MANDATORY)
public interface UserRepository extends HibernateRepository<User>, JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String email);

    Optional<User> findUserByUsernameOrEmail(String username, String email);
}
