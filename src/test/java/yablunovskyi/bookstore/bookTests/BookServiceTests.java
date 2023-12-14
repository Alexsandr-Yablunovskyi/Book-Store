package yablunovskyi.bookstore.bookTests;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import yablunovskyi.bookstore.dto.book.BookRequestDto;
import yablunovskyi.bookstore.model.Book;
import yablunovskyi.bookstore.model.Category;
import yablunovskyi.bookstore.repository.BookRepository;
import yablunovskyi.bookstore.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;
    
    @InjectMocks
    private BookService bookService;
    
    /*@Test
    @DisplayName("""
            Verify that information about book with specify id was updated when book exists.
            """)
    public void updateBook_withValidBookId_ShouldReturnValidBookInformation() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(1L);
        book.setTitle("test");
        book.setAuthor("test");
        book.setCategories();
        BookRequestDto dto = new BookRequestDto(
                "test book",
                "test author",
                1L,
                "1234567891",
                400,
                "Test description",
                "test link");
        Mockito.when(bookRepository.findById(bookId)).thenReturn()
    }*/

}
