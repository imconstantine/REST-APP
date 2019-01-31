package com.imconstantine.netexam.utils.validator;

import com.imconstantine.netexam.utils.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuestionsCountPerExamValidator implements ConstraintValidator<QuestionsCountPerExamConstraint, Integer> {

    @Override
    public boolean isValid(Integer field, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (field == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.EXAM_QUESTIONS_COUNT_IS_NULL.toString()).addConstraintViolation();
            return false;
        }
        /*} else if (field < Constants.MIN_QUESTIONS_COUNT_PER_EXAM) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(ErrorCode.EXAM_QUESTIONS_COUNT_LESS_THAN_ALLOWED.toString()).addConstraintViolation();
            return false;
        }*/
        return true;
    }
}
