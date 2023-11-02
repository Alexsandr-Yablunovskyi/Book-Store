package yablunovskyi.bookstore.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import yablunovskyi.bookstore.dto.book.BookDto;
import yablunovskyi.bookstore.dto.book.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);
    
    BookDto findById(Long id);
    
    List<BookDto> findAll(Pageable pageable);
    
    BookDto updateById(Long id, CreateBookRequestDto requestDto);
    
    void deleteById(Long id);
}
