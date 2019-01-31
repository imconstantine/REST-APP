package com.imconstantine.netexam.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GroupValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GroupConstraint {
    String message() default "Invalid group";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}