package com.imconstantine.netexam.utils.validator;

import com.imconstantine.netexam.utils.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PatronymicValidator implements ConstraintValidator<PatronymicConstraint, String> {

    @Override
    public void initialize(PatronymicConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (field == null) {
            return true;
        } else {
            if (!Constants.VALID_NAME_WORD.matcher(field).find()) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.INVALID_PATRONYMIC.toString()).addConstraintViolation();
                return false;
            } else if (field.length() > Constants.MAX_NAME_LENGTH) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.PATRONYMIC_TOO_LONG.toString()).addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}
