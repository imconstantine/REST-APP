package com.imconstantine.netexam.utils.validator;

import com.imconstantine.netexam.utils.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LastNameValidator implements ConstraintValidator<LastNameConstraint, String> {

    @Override
    public void initialize(LastNameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (field == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.LASTNAME_IS_NULL.toString()).addConstraintViolation();
            return false;
        }
        if (!Constants.VALID_NAME_WORD.matcher(field).find()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.INVALID_LASTNAME.toString()).addConstraintViolation();
            return false;
        } else if (field.length() > Constants.MAX_NAME_LENGTH) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.LASTNAME_TOO_LONG.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
