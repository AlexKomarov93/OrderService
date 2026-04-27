package com.example.OrderService.services;

import com.example.OrderService.dto.TokenResponseDto;
import com.example.OrderService.dto.UsersResponseDto;

public interface UserService {

    UsersResponseDto registerUser(String username, String password, String email);

    TokenResponseDto login(String username, String password);

    TokenResponseDto refreshToken(String oldRefreshToken);

}
