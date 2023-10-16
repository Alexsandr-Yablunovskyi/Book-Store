package yablunovskyi.bookstore.repository;

import java.util.List;
import yablunovskyi.bookstore.model.Book;

public interface BookRepository {
    Book save(Book book);
    
    List<Book> findAll();
}
