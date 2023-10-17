package yablunovskyi.bookstore.repository;

import java.util.List;
import java.util.Optional;
import yablunovskyi.bookstore.model.Book;

public interface BookRepository {
    Book save(Book book);
    
    Optional<Book> findById(Long id);
    
    List<Book> findAllByAuthor(String author);
    
    List<Book> findAll();
}
