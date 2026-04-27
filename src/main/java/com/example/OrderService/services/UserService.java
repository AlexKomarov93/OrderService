package com.example.OrderService.services;

import com.example.OrderService.entity.Users;

public interface UserService {
    Users registerUser(String username, String password, String email);

    TokenResponseDto login(String username, String password);

    TokenResponseDto refreshToken(String oldRefreshToken);
}
