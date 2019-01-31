package com.imconstantine.netexam.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FirstNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FirstNameConstraint {
    String message() default "Invalid firstName";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
