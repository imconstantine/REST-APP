package com.imconstantine.netexam.utils.validator;

import com.imconstantine.netexam.utils.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimeInMinutesValidator implements ConstraintValidator<TimeInMinutesConstraint, Integer> {

    @Override
    public boolean isValid(Integer field, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (field == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.EXAM_TIME_IS_NULL.toString()).addConstraintViolation();
            return false;
        }
        /*} else if (field < Constants.MIN_TIME) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.EXAM_TIME_LESS_THAN_ALLOWED.toString()).addConstraintViolation();
            return false;
        }*/
        return true;
    }
}
