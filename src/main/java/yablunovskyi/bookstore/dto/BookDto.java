package yablunovskyi.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.URL;

public record BookDto(
        @NotBlank
        Long id,
        @NotBlank
        String title,
        @NotBlank
        String author,
        @NotBlank
        String isbn,
        @NotNull
        @Min(0)
        BigDecimal price,
        @Size(min = 10, max = 500)
        String description,
        @URL
        String coverImage
) {
}
