package yablunovskyi.bookstore.dto.order;

import jakarta.validation.constraints.NotBlank;

public record UpdateStatusRequestDto(
        @NotBlank String Status
) {
}
