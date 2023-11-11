package com.project.order.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IntegerValidator.class)
public @interface ValidateInput {

    public String message() default "Invalid input. Please provide integer value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
