package yablunovskyi.bookstore.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import yablunovskyi.bookstore.dto.book.BookRequestDto;
import yablunovskyi.bookstore.dto.book.BookResponseDto;
import yablunovskyi.bookstore.dto.book.BookResponseDtoWithoutCategoryIds;

public interface BookService {
    BookResponseDto save(BookRequestDto requestDto);
    
    BookResponseDto findById(Long id);
    
    List<BookResponseDto> findAll(Pageable pageable);
    
    BookResponseDto updateById(Long id, BookRequestDto requestDto);
    
    void deleteById(Long id);
    
    List<BookResponseDtoWithoutCategoryIds> findBooksByCategoryId(Long id);
}
