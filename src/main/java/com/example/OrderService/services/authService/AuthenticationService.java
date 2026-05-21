package com.example.OrderService.services.authService;

import com.example.OrderService.dto.request.TokenRefreshRequest;
import com.example.OrderService.dto.response.JwtAuthenticationResponse;
import com.example.OrderService.dto.request.SignInRequest;
import com.example.OrderService.dto.request.SignUpRequest;
import com.example.OrderService.entity.enums.Role;
import com.example.OrderService.entity.Users;
import com.example.OrderService.services.jwt.JwtService;
import com.example.OrderService.services.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        var user = Users.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new JwtAuthenticationResponse(jwt, refreshToken);
    }

    public JwtAuthenticationResponse refreshToken(TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        String username = jwtService.extractUserName(refreshToken);

        var user = userService
                .userDetailsService()
                .loadUserByUsername(username);

        if (jwtService.isTokenValid(refreshToken, user)) {
            var newAccessToken = jwtService.generateToken(user);
            var newRefreshToken = jwtService.generateRefreshToken(user);

            return new JwtAuthenticationResponse(newAccessToken, newRefreshToken);
        }

        throw new RuntimeException("Refresh token is not valid!");
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new JwtAuthenticationResponse(jwt, refreshToken);
    }
}

