package yablunovskyi.bookstore.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import yablunovskyi.bookstore.dto.book.BookRequestDto;
import yablunovskyi.bookstore.dto.book.BookResponseDto;
import yablunovskyi.bookstore.dto.book.BookResponseDtoWithoutCategoryIds;
import yablunovskyi.bookstore.model.Book;
import yablunovskyi.bookstore.model.Category;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface BookMapper {
    @Mapping(source = "categories", target = "categoriesId")
    BookResponseDto toDto(Book book);
    
    Book toBook(BookRequestDto requestDto);
    
    void updateRequestDtoToBook(BookRequestDto requestDto, @MappingTarget Book book);
    
    BookResponseDtoWithoutCategoryIds toDtoWithoutCategories(Book book);
    
    default Long getCategoryId(Category category) {
        return category.getId();
    }
}
