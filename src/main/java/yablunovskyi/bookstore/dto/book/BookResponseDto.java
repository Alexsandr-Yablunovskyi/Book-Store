package yablunovskyi.bookstore.dto.book;

import java.math.BigDecimal;
import java.util.Set;
import yablunovskyi.bookstore.model.Category;

public record BookResponseDto(
        Long id,
        String title,
        String author,
        Set<Category> categories,
        String isbn,
        BigDecimal price,
        String description,
        String coverImage
) {
}
