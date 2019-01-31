package com.imconstantine.netexam.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = QuestionsCountPerExamValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QuestionsCountPerExamConstraint {
    String message() default "Invalid department";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
