package com.todo.todobackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class TodoApiException extends RuntimeException{

    private HttpStatus status;
    private String message;
}
