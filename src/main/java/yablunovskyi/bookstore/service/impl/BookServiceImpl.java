package yablunovskyi.bookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yablunovskyi.bookstore.dto.BookDto;
import yablunovskyi.bookstore.dto.CreateBookRequestDto;
import yablunovskyi.bookstore.exception.EntityNotFoundException;
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
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: %d".formatted(id)))
        );
    }
    
    /*@Override
    public List<BookDto> findAllByAuthor(String author) {
        return bookRepository.findAllByAuthor(author).stream()
                .map(bookMapper::toDto)
                .toList();
    }*/
    
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }
    
    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
