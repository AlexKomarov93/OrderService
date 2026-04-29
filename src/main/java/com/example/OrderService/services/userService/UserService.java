package com.example.OrderService.services.userService;

import com.example.OrderService.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    Users save(Users user);

    Users create(Users user);

    Users getByUsername(String username);

    UserDetailsService userDetailsService();

    Users getCurrentUser();
}
