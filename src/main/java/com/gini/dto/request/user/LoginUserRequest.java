package com.gini.dto.request.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LoginUserRequest(

        @NotNull
        String usernameOrEmail,

        @Pattern(regexp = "^[A-Za-z0-9_\\\\-\\\\.\\\\!@&]+$")
        @NotNull
        String password
) {
}
