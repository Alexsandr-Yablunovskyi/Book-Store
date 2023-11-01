package yablunovskyi.bookstore.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequestDto {
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "must match pattern 'username@domain.com'")
    private String email;
    
    @NotBlank(message = "can't be blank or null")
    @Size(min = 8, max = 180, message = "length should be from 8 to 180")
    private String password;
    
    @NotBlank(message = "can't be blank or null")
    @Size(min = 8, max = 180, message = "length should be from 8 to 180")
    private String repeatPassword;
    
    @NotBlank(message = "can't be blank or null")
    private String firstName;
    
    @NotBlank(message = "can't be blank or null")
    private String lastName;
    private String shippingAddress;
}
