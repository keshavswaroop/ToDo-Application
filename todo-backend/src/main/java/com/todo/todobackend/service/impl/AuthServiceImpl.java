package com.todo.todobackend.service.impl;

import com.todo.todobackend.dto.JwtAuthResponse;
import com.todo.todobackend.dto.LoginDto;
import com.todo.todobackend.dto.RegisterDto;
import com.todo.todobackend.entity.Role;
import com.todo.todobackend.entity.User;
import com.todo.todobackend.exception.TodoApiException;
import com.todo.todobackend.repository.RoleReopsitory;
import com.todo.todobackend.repository.UserRepository;
import com.todo.todobackend.security.JwtTokenProvider;
import com.todo.todobackend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleReopsitory roleReopsitory;
    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {

        //Check for username exists in the database or not
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        //create user object to store in the database

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        //set roles for the user
        Set<Role> roles = new HashSet<>();
        Role userRole = roleReopsitory.findByName("ROLE_USER");
        roles.add(userRole);

        //add role to user
        user.setRoles(roles);

        //save the data to database
        userRepository.save(user);
        return "User Successfully registered";
    }

    private AuthenticationManager authenticationManager;
    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        )); //Here Authentication is from class: org.springframework.security.core

        //Store authentication in security context holder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //generate token
        String token = jwtTokenProvider.getToken(authentication);

        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(),loginDto.getUsernameOrEmail());

        String role = null;

        if(optionalUser.isPresent()){
            User loggedInUser = optionalUser.get();
            Optional<Role> optionalRole = loggedInUser.getRoles().stream().findFirst();

            if(optionalRole.isPresent()) {
                Role userRole = optionalRole.get();
                role = userRole.getName();
            }
        }

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);

        return jwtAuthResponse;
    }
}
