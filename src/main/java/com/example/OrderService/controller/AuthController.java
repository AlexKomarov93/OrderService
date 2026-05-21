package com.example.OrderService.controller;

import com.example.OrderService.dto.request.SignInRequest;
import com.example.OrderService.dto.request.SignUpRequest;
import com.example.OrderService.dto.request.TokenRefreshRequest;
import com.example.OrderService.dto.response.JwtAuthenticationResponse;
import com.example.OrderService.services.authService.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/reg")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/refresh")
    public JwtAuthenticationResponse refresh(@RequestBody @Valid TokenRefreshRequest request) {
        return authenticationService.refreshToken(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}



