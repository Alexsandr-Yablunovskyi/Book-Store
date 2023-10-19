package yablunovskyi.bookstore.service;

import java.util.List;
import yablunovskyi.bookstore.dto.BookDto;
import yablunovskyi.bookstore.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);
    
    BookDto findById(Long id);
    
    List<BookDto> findAll();
    
    BookDto updateById(Long id, CreateBookRequestDto requestDto);
    
    void deleteById(Long id);
}
