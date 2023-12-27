package yablunovskyi.bookstore.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;
import yablunovskyi.bookstore.model.Book;
import yablunovskyi.bookstore.model.Category;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepoTests {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Test
    @DisplayName("""
            Find all books with specify id""")
    @Sql(scripts = {
            "classpath:database/books/add-book-to-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/remove-book-by-id-from-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByCategoriesId_ValidId_ReturnsValidBookList() {
        //Given
        Long category1Id = 1L;
        Long category2Id = 2L;
        Category category1 = new Category();
        category1.setId(category1Id);
        Category category2 = new Category();
        category2.setId(category2Id);
        
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("title");
        book1.setAuthor("author");
        book1.setCategories(Set.of(category1, category2));
        book1.setIsbn("1234567890");
        book1.setPrice(BigDecimal.valueOf(300));
        book1.setDescription("description");
        book1.setCoverImage("link");
        
        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("title 2");
        book2.setAuthor("author 2");
        book2.setCategories(Set.of(category1));
        book2.setIsbn("1234567892");
        book2.setPrice(BigDecimal.valueOf(200));
        book2.setDescription("description 2");
        book2.setCoverImage("link 2");
        
        bookRepository.save(book1);
        bookRepository.save(book2);
        
        //When
        List<Book> actual = bookRepository.findAllByCategoriesId(category2Id);
        
        //Then
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals("test title", actual.get(0).getTitle());
    }
}
