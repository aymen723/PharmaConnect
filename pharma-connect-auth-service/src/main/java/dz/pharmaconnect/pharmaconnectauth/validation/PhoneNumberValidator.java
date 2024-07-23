package dz.pharmaconnect.pharmaconnectauth.validation;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidator;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private static final String PHONE_NUMBER_PATTERN = "^\\+?[0-9. ()-]{7,25}$";
    private static final Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null) {
            return true;
        }
        return pattern.matcher(phoneNumber).matches();
    }
}