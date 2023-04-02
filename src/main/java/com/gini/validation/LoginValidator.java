package com.gini.validation;

import com.gini.dto.request.UserLoginRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;

import static com.gini.constants.LogicCoreConstants.EMAIL_PATTERN;
import static com.gini.constants.LogicCoreConstants.USERNAME_PATTERN;


public class LoginValidator implements ConstraintValidator<UserValidation, UserLoginRequest> {


    @Override
    public boolean isValid(UserLoginRequest loginRequest, ConstraintValidatorContext context) {

        Matcher emailMatcher = EMAIL_PATTERN.matcher(loginRequest.usernameOrEmail());
        Matcher usernameMatcher = USERNAME_PATTERN.matcher(loginRequest.usernameOrEmail());

        if (emailMatcher.matches()) {
            return true;
        }

        context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Invalid username must match: ^[A-Za-z]{5,60}$")
                        .addPropertyNode("usernameOrEmail")
                .addConstraintViolation();

        return usernameMatcher.matches();
    }
}
