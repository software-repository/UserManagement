package com.jas.validators;

import com.jas.exceptions.BirthDateValidationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


@Component
public class BirthDateValidator implements ConstraintValidator<BirthDate, Date> {


    @Override
    public void initialize(BirthDate constraintAnnotation) {

    }
    @Override
    public boolean isValid(final Date valueToValidate, ConstraintValidatorContext context) {
        if(valueToValidate==null)
            throw new BirthDateValidationException("Birth date cannot be null");
        Calendar dateInCalendar = Calendar.getInstance();
        dateInCalendar.setTime(valueToValidate);

        int age = Calendar.getInstance().get(Calendar.YEAR) - dateInCalendar.get(Calendar.YEAR);
        return age >= 18 && age<=60;
    }
}
