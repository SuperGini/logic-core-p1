package com.gini.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserLoginRequest(

        @NotNull
        String usernameOrEmail,

        @Pattern(regexp = "^[A-Za-z0-9_\\\\-\\\\.\\\\!@&]+$")
        @NotNull
        String password
) {
}
