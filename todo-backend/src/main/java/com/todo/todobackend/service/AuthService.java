package com.todo.todobackend.service;

import com.todo.todobackend.dto.JwtAuthResponse;
import com.todo.todobackend.dto.LoginDto;
import com.todo.todobackend.dto.RegisterDto;

public interface AuthService {

    String register(RegisterDto registerDto);

    JwtAuthResponse login(LoginDto loginDto);
}
