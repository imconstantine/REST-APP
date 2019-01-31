package com.imconstantine.netexam.utils.validator;

import com.imconstantine.netexam.utils.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DepartmentValidator implements ConstraintValidator<DepartmentConstraint, String> {

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (field == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.DEPARTMENT_IS_NULL.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}