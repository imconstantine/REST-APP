package com.imconstantine.netexam.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LastNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LastNameConstraint {
    String message() default "Invalid lastName";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
