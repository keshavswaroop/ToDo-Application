package com.todo.todobackend.service.impl;

import com.todo.todobackend.dto.TodoDto;
import com.todo.todobackend.entity.Todo;
import com.todo.todobackend.exception.ResourceNotFoundException;
import com.todo.todobackend.mapper.TodoMapper;
import com.todo.todobackend.repository.TodoRepository;
import com.todo.todobackend.service.TodoService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    @Override
    public TodoDto createTodo(TodoDto todoDto) {
        Todo todo = TodoMapper.mapToTodo(todoDto);
        Todo savedTodo = todoRepository.save(todo);
        return TodoMapper.mapToTodoDto(savedTodo);
    }

    @Override
    public TodoDto getTodoById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("There is no Todo at id: "+id));
        return TodoMapper.mapToTodoDto(todo);
    }

    @Override
    public List<TodoDto> getAllTodo() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map((todo)->TodoMapper.mapToTodoDto(todo)).collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(Long id, TodoDto todoDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Todo is not present at id: "+id));
        
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        Todo updatedTodo = todoRepository.save(todo);
        return TodoMapper.mapToTodoDto(updatedTodo);
    }

    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No Todo found at the id: "+ id));
        todoRepository.deleteById(id);
    }

    @Override
    public TodoDto completeTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No Todo present at id: "+ id));
        todo.setCompleted(true);

        Todo savedTodo = todoRepository.save(todo);
        return TodoMapper.mapToTodoDto(savedTodo);
    }

    @Override
    public TodoDto incompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No Todo present at id: "+ id));
        todo.setCompleted(false);
        Todo savedTodo = todoRepository.save(todo);
        return TodoMapper.mapToTodoDto(savedTodo);
    }


}
