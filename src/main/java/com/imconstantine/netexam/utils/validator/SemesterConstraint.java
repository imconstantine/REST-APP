package com.imconstantine.netexam.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SemesterValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SemesterConstraint {
    String message() default "Invalid semester";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
