package dz.pharmaconnect.pharmaconnectstockservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private static final String PHONE_NUMBER_PATTERN = "^(05|06|07)\\d{8}$";
    private Pattern pattern;

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null) return true;
        if (phoneNumber.isEmpty()) {
            return false; // or true, depending on whether you want to allow empty values
        }
        return pattern.matcher(phoneNumber).matches();
    }
}
