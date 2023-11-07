package yablunovskyi.bookstore.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import yablunovskyi.bookstore.dto.book.BookRequestDto;
import yablunovskyi.bookstore.dto.book.BookResponseDto;
import yablunovskyi.bookstore.dto.book.BookResponseDtoWithoutCategoryIds;
import yablunovskyi.bookstore.model.Book;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface BookMapper {
    BookResponseDto toDto(Book book);
    
    Book toBook(BookRequestDto requestDto);
    
    void updateRequestDtoToBook(BookRequestDto requestDto, @MappingTarget Book book);
    
    BookResponseDtoWithoutCategoryIds toDtoWithoutCategories(Book book);
    // for the GET /api/categories/{id}/books (Retrieve books by a specific category)
    
    //@AfterMapping default
    void setCategoryIds(@MappingTarget BookResponseDto bookDto, Book book);
}
