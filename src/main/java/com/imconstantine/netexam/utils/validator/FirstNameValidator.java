package com.imconstantine.netexam.utils.validator;

import com.imconstantine.netexam.utils.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FirstNameValidator implements ConstraintValidator<FirstNameConstraint, String> {

    @Override
    public void initialize(FirstNameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (field == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.FIRSTNAME_IS_NULL.toString()).addConstraintViolation();
            return false;
        }
        if (!Constants.VALID_NAME_WORD.matcher(field).find()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.INVALID_FIRSTNAME.toString()).addConstraintViolation();
            return false;
        } else if (field.length() > Constants.MAX_NAME_LENGTH) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.FIRSTNAME_TOO_LONG.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
