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
    
    /*@Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("The garden of Eden");
            book.setAuthor("Hemingway");
            book.setIsbn("1234567");
            book.setPrice(BigDecimal.valueOf(650));
            book.setDescription("Charming book");
            bookService.save(book);
            System.out.println(bookService.findAll());
        };
    }*/
}
