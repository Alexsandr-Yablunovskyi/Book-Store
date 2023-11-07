package yablunovskyi.bookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDto(
        @NotBlank(message = "can't be blank or null")
        String name,
        @Size(min = 10, max = 500, message = "must have some useful information")
        String description
) {
}
