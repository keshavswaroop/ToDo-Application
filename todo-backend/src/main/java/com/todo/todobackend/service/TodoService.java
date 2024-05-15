package com.todo.todobackend.service;

import com.todo.todobackend.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto createTodo(TodoDto todoDto);

    TodoDto getTodoById(Long id);

    List<TodoDto> getAllTodo();

    TodoDto updateTodo(Long id, TodoDto todoDto);

    void deleteTodo(Long id);

    TodoDto completeTodo(Long id);

    TodoDto incompleteTodo(Long id);
}
