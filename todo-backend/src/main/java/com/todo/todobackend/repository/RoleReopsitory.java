package com.todo.todobackend.repository;

import com.todo.todobackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleReopsitory extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
