package com.imconstantine.netexam.utils.validator;

import com.imconstantine.netexam.utils.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SemesterValidator implements ConstraintValidator<SemesterConstraint, Integer> {

    @Override
    public void initialize(SemesterConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer field, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (field == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.SEMESTER_IS_NULL.toString()).addConstraintViolation();
            return false;
        }
        if (field < 1 || field > 12) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.SEMESTER_BAD_VALUE.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
