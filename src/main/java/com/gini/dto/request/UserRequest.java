package com.gini.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserRequest(

        @Pattern(regexp = "^[A-Za-z]{5,60}$")
        @NotNull
        String username,

        /**
         * https://www.baeldung.com/java-email-validation-regex
         * */
        @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        @NotNull
        String email,

        @Pattern(regexp = "^[A-Za-z0-9_\\\\-\\\\.\\\\!@&]+$")
        @NotNull
        String password
) {
}
