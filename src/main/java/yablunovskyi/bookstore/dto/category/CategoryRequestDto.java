package yablunovskyi.bookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDto(
        @NotBlank(message = "can't be blank or null")
        String name,
        @Size(min = 10, max = 255, message =
                "must have more than 10 and less than 255 symbols length"
                + "and have some useful information")
        String description
) {
}
