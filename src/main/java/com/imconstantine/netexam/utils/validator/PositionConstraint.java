package com.imconstantine.netexam.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PositionValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PositionConstraint {
    String message() default "Invalid firstName";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}