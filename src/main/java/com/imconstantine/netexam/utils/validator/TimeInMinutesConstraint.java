package com.imconstantine.netexam.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimeInMinutesValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeInMinutesConstraint {
    String message() default "Invalid department";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
