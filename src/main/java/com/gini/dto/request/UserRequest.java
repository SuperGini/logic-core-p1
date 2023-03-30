package com.gini.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @Pattern(regexp = "^[A-Za-z]+$")
        @Size(min = 5, max = 60)
        @NotNull
        String username,

        @Email
        @NotNull
        String email,

        @Pattern(regexp = "^[A-Za-z0-9_\\\\-\\\\.\\\\!@&]+$")
        @NotNull
        String password
) {
}
