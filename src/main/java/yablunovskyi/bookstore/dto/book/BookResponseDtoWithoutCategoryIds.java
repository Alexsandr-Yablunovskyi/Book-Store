package yablunovskyi.bookstore.dto.book;

import java.math.BigDecimal;
// use as a response for the endpoint
// GET: /api/categories/{id}/books (Retrieve books by a specific category)

public record BookResponseDtoWithoutCategoryIds(
        Long id,
        String title,
        String author,
        String isbn,
        BigDecimal price,
        String description,
        String coverImage
) {
}
