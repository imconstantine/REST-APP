package com.imconstantine.netexam.utils.validator;

import com.imconstantine.netexam.utils.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (field == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.PASSWORD_IS_NULL.toString()).addConstraintViolation();
            return false;
        }
        if (field.length() == 0) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.PASSWORD_IS_NULL.toString()).addConstraintViolation();
            return false;
        }
        if (field.length() < Constants.MIN_PASSWORD_LENGTH || field.length() > Constants.MAX_NAME_LENGTH) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.PASSWORD_WRONG_LENGTH.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
