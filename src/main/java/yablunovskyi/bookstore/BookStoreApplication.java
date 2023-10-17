package yablunovskyi.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import yablunovskyi.bookstore.service.BookService;

@SpringBootApplication
public class BookStoreApplication {
    private final BookService bookService;
    
    public BookStoreApplication(BookService bookService) {
        this.bookService = bookService;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }
}
