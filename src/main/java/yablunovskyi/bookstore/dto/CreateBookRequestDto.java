package yablunovskyi.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.URL;

public record CreateBookRequestDto(
        @NotBlank(message = "can't be blank or null")
        String title,
        @NotBlank(message = "can't be blank or null")
        String author,
        @Pattern(regexp = "^(?=(?:[^0-9]*[0-9]){10}(?:(?:[^0-9]*[0-9]){3})?$)[\\d-]+$",
                message = "must contain 10 digits and may or may not contain a hyphen")
        String isbn,
        @NotNull
        @PositiveOrZero(message = "can't be less than 0")
        BigDecimal price,
        @Size(min = 10, max = 500, message = "must have some useful information")
        String description,
        @URL(message = "must contain a URL that links to the book image")
        String coverImage
) {
}
