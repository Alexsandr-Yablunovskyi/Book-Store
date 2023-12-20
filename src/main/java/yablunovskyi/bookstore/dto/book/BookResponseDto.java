package yablunovskyi.bookstore.dto.book;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private Set<Long> categoriesIds;
    private String isbn;
    private BigDecimal price;
    private String description;
    private String coverImage;
}
