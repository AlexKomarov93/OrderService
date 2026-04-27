package com.example.OrderService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя пользователя не может быть пустым или содержать одни пробелы")
    @Column(name = "username", length = 40, nullable = false,unique = true)
    private String username;

    @NotBlank(message = "Пароль не может быть пустым или содержать одни пробелы")
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @NotBlank(message = "Почтовый адрес не может быть пустым или содержать одни пробелы")
    @Email
    @Column(name = "email", length = 60, nullable = false,unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
}
