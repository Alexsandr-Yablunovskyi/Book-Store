package yablunovskyi.bookstore.repository;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import yablunovskyi.bookstore.model.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {
        "classpath:database/categories/add-default-categories.sql",
        "classpath:database/books/add-default-books.sql"
}
)
@Sql(scripts = {
        "classpath:database/categories/delete-all-categories.sql",
        "classpath:database/books/delete-all-books.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookRepositoryTests {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Test
    @DisplayName("""
            Verify findAllByCategoriesId() method works
            and returns all books with the specified existed category id""")
    /*@Sql(scripts = {
            "classpath:database/categories/add-default-categories.sql",
            "classpath:database/books/add-default-books.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/delete-all-categories.sql",
            "classpath:database/books/delete-all-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)*/
    public void findAllByCategoriesId_ValidId_ReturnsValidBookList() {
        List<Book> actual = bookRepository.findAllByCategoriesId(1L);
    
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals("first test title", actual.get(0).getTitle());
        Assertions.assertEquals("third test title", actual.get(1).getTitle());
    }
    
    @Test
    @DisplayName("""
            Verify findAllByCategoriesId() method works
            and returns empty list when category id doesn't exist""")
    /*@Sql(scripts = {
            "classpath:database/categories/add-default-categories.sql",
            "classpath:database/books/add-default-books.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/delete-all-categories.sql",
            "classpath:database/books/delete-all-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)*/
    public void findAllByCategoriesId_InvalidId_ReturnsEmptyList() {
        List<Book> actual = bookRepository.findAllByCategoriesId(10L);
        
        Assertions.assertEquals(0, actual.size());
    }
}
