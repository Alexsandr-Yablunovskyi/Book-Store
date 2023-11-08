package yablunovskyi.bookstore.dto.book;

import java.math.BigDecimal;
import java.util.List;

public record BookResponseDto(
        Long id,
        String title,
        String author,
        List<Long> categoriesId,
        String isbn,
        BigDecimal price,
        String description,
        String coverImage
) {
}
