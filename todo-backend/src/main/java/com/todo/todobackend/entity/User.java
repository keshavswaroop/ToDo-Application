package com.todo.todobackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    private String name;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)    //FetchType.EAGER means that the Many to many relationship, is established as soon as the User loads up. And Cascade.ALl refers that when saved the roles will also be saved
    @JoinTable(name="user_role",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "uid"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "rid"))
    private Set<Role> roles;
}
