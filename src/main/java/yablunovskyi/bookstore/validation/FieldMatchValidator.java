package yablunovskyi.bookstore.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import yablunovskyi.bookstore.dto.user.UserRegistrationRequestDto;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch,
        UserRegistrationRequestDto> {
    @Override
    public boolean isValid(UserRegistrationRequestDto requestDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return requestDto != null
                && requestDto.password().equals(requestDto.repeatPassword());
    }
}
