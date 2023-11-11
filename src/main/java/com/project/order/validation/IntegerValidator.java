package com.project.order.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IntegerValidator implements ConstraintValidator<ValidateInput,Number> {

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {
        return number instanceof Integer ;
    }
}
