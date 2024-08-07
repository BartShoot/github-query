package com.bartshoot.github_query.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GitHubUserNameValidator implements ConstraintValidator<ValidGitHubUserName, String> {

    @Override
    public boolean isValid(String userName, ConstraintValidatorContext context) {
        if (userName == null) {
            return false;
        }
        boolean isValid = userName.matches("^[a-zA-Z\\\\d](?:[a-zA-Z\\\\d]|-(?=[a-zA-Z\\\\d])){0,38}$");
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid username: " + userName)
                    .addConstraintViolation();
        }
        return isValid;
    }
}
