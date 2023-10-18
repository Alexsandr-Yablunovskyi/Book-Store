package yablunovskyi.bookstore.dto;

import java.math.BigDecimal;
import lombok.Data;


public record BookDto(
    Long id,
    String title,
    String author,
    String isbn,
    BigDecimal price,
    String description,
    String coverImage
) {
}
