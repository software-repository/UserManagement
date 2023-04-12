package com.jas.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;
import java.util.Date;

public class BirthDateValidator implements ConstraintValidator<BirthDate, Date> {
    @Override
    public boolean isValid(final Date valueToValidate, ConstraintValidatorContext context) {
        Calendar dateInCalendar = Calendar.getInstance();
        dateInCalendar.setTime(valueToValidate);

        int age = Calendar.getInstance().get(Calendar.YEAR) - dateInCalendar.get(Calendar.YEAR);
        return age >= 18 && age<=60;
    }
}
