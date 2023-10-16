package yablunovskyi.bookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yablunovskyi.bookstore.model.Book;
import yablunovskyi.bookstore.repository.BookRepository;
import yablunovskyi.bookstore.service.BookService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    
    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }
    
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
