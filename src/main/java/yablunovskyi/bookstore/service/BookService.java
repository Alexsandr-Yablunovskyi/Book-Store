package yablunovskyi.bookstore.service;

import java.util.List;
import yablunovskyi.bookstore.dto.BookDto;
import yablunovskyi.bookstore.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);
    
    BookDto findById(Long id);
    
    List<BookDto> findAllByAuthor(String author);
    
    List<BookDto> findAll();
}
