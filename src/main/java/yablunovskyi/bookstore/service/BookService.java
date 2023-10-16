package yablunovskyi.bookstore.service;

import java.util.List;
import yablunovskyi.bookstore.model.Book;

public interface BookService {
    Book save(Book book);
    
    List<Book> findAll();
}
