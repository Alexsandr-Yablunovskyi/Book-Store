package yablunovskyi.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yablunovskyi.bookstore.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
