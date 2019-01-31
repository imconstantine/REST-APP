package com.imconstantine.netexam.utils.validator;

import com.imconstantine.netexam.utils.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginValidator implements ConstraintValidator<LoginConstraint, String> {

    @Override
    public void initialize(LoginConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (field == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.LOGIN_IS_NULL.toString()).addConstraintViolation();
            return false;
        }
        if (!Constants.VALID_LOGIN.matcher(field).find()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.INVALID_LOGIN.toString()).addConstraintViolation();
            return false;
        } else if (field.length() > Constants.MAX_NAME_LENGTH) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.LOGIN_TOO_LONG.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
