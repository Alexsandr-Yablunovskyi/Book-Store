package yablunovskyi.bookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yablunovskyi.bookstore.dto.BookDto;
import yablunovskyi.bookstore.dto.CreateBookRequestDto;
import yablunovskyi.bookstore.mapper.BookMapper;
import yablunovskyi.bookstore.model.Book;
import yablunovskyi.bookstore.repository.BookRepository;
import yablunovskyi.bookstore.service.BookService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    
    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }
    
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
