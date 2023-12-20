package yablunovskyi.bookstore.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

public record BookRequestDto(
        @NotBlank(message = "can't be blank or null")
        String title,
        @NotBlank(message = "can't be blank or null")
        String author,
        @NotEmpty
        Set<@Positive Long> categoriesIds,
        
        /*@NotEmpty
        List<@Positive Long> categoriesIds,*/
        @Pattern(regexp = "^(?=(?:[^0-9]*[0-9]){10}(?:(?:[^0-9]*[0-9]){3})?$)[\\d-]+$",
                message = "must contain 10 digits and may or may not contain a hyphen")
        String isbn,
        @NotNull
        @PositiveOrZero(message = "can't be less than 0")
        BigDecimal price,
        @Size(min = 10, max = 500, message = "must have some useful information")
        String description,
        @NotBlank(message = "can't be blank or null")
        String coverImage
) {
}
