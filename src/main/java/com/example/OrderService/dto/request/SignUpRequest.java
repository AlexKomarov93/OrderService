package com.example.OrderService.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 3, max = 40, message = "Имя пользователя должно содержать от 3 до 40 символов")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, max = 60, message = "Длина пароля должна быть от 8 до 60 символов")
    private String password;

    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    @Size(min = 5, max = 60, message = "Адрес электронной почты должен содержать от 5 до 60 символов")
    private String email;

}

