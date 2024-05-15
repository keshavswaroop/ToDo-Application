package com.todo.todobackend.repository;

import com.todo.todobackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);     //The findBy retrives the details using username

    Boolean existsByEmail(String email);    //The existsBy checks if the email exists or not

    Optional<User> findByUsernameOrEmail(String username, String email);    //this retrives the details based on username or email

    Boolean existsByUsername(String username);
}
