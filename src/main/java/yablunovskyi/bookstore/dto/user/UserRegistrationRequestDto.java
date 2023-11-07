package yablunovskyi.bookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import yablunovskyi.bookstore.validation.FieldMatch;

@FieldMatch(fields = {"password", "repeatPassword"}, message = "Password mismatch")
public record UserRegistrationRequestDto(
        @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
                message = "must match pattern 'username@domain.com'")
        String email,
        
        @NotBlank(message = "can't be blank or null")
        @Size(min = 8, max = 180, message = "length should be from 8 to 180")
        String password,
        
        @NotBlank(message = "can't be blank or null")
        @Size(min = 8, max = 180, message = "length should be from 8 to 180")
        String repeatPassword,
        
        @NotBlank(message = "can't be blank or null")
        String firstName,
        
        @NotBlank(message = "can't be blank or null")
        String lastName,
        String shippingAddress
) {
}
