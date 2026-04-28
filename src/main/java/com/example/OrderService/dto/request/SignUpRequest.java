package com.example.OrderService.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

    @Size(min = 3, max = 40, message = "Имя пользователя должно содержать от 3 до 40 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Size(max = 60, message = "Длина пароля должна быть не более 60 символов")
    private String password;

    @Size(min = 5, max = 60, message = "Адрес электронной почты должен содержать от 5 до 60 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

}
