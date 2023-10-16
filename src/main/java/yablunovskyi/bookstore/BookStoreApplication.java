package yablunovskyi.bookstore;

import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import yablunovskyi.bookstore.model.Book;
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
    
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("The garden of Eden");
            book.setAuthor("Hemingway");
            book.setIsbn("1234567");
            book.setPrice(BigDecimal.valueOf(550));
            book.setDescription("Charming book");
            bookService.save(book);
            System.out.println(bookService.findAll());
        };
    }
}
