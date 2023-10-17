package yablunovskyi.bookstore.mapper;

import org.mapstruct.Mapper;
import yablunovskyi.bookstore.config.MapperConfig;
import yablunovskyi.bookstore.dto.BookDto;
import yablunovskyi.bookstore.dto.CreateBookRequestDto;
import yablunovskyi.bookstore.model.Book;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);
    
    Book toModel(CreateBookRequestDto requestDto);
}
