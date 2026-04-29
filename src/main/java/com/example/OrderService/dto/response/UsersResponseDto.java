package com.example.OrderService.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UsersResponseDto(
        @JsonProperty("username")
        String username,

        @JsonProperty("email")
        String email
) {
}
