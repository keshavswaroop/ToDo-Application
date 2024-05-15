package com.todo.todobackend.controller;

import com.todo.todobackend.dto.TodoDto;
import com.todo.todobackend.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@AllArgsConstructor
@CrossOrigin("*")
public class TodoController {
    private TodoService todoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto) {
        TodoDto savedTodo = todoService.createTodo(todoDto);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id){
        TodoDto todoDto = todoService.getTodoById(id);
        return ResponseEntity.ok(todoDto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todo = todoService.getAllTodo();
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Long id,@RequestBody TodoDto todoDto) {
        TodoDto updatedTodos = todoService.updateTodo(id,todoDto);
        return ResponseEntity.ok(updatedTodos);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return "Todo deleted Successfully";
    }
    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<TodoDto> completedTodo(@PathVariable Long id) {
        TodoDto todoDto = todoService.completeTodo(id);
        return ResponseEntity.ok(todoDto);
    }
    @PatchMapping("/{id}/incomplete")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<TodoDto> incompleteTodo(@PathVariable Long id) {
        TodoDto todoDto = todoService.incompleteTodo(id);
        return ResponseEntity.ok(todoDto);
    }
}
