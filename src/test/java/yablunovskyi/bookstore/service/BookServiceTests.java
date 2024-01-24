package yablunovskyi.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import yablunovskyi.bookstore.dto.book.BookRequestDto;
import yablunovskyi.bookstore.dto.book.BookResponseDto;
import yablunovskyi.bookstore.dto.book.BookResponseDtoWithoutCategoryIds;
import yablunovskyi.bookstore.mapper.BookMapper;
import yablunovskyi.bookstore.model.Book;
import yablunovskyi.bookstore.model.Category;
import yablunovskyi.bookstore.repository.BookRepository;
import yablunovskyi.bookstore.repository.CategoryRepository;
import yablunovskyi.bookstore.service.impl.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;
    
    @Mock
    private CategoryRepository categoryRepository;
    
    @Mock
    private BookMapper bookMapper;
    
    @InjectMocks
    private BookServiceImpl bookService;
    
    @Test
    @DisplayName("""
            Verify save() method works properly""")
    public void save_ValidBookRequestDto_ReturnsValidBookResponseDto() {
        //Given
        BookRequestDto requestDto = new BookRequestDto(
                "title",
                "author",
                Set.of(1L, 2L),
                "1234567890",
                BigDecimal.valueOf(400),
                "description",
                "link"
        );
        
        Long bookId = 1L;
        Set<Category> categories = requestDto.categoriesIds().stream()
                .map(id -> {
                    Category category = new Category();
                    category.setId(id);
                    return category;
                })
                .collect(Collectors.toSet());
        
        Book book = new Book();
        book.setId(bookId);
        book.setTitle(requestDto.title());
        book.setAuthor(requestDto.author());
        book.setCategories(categories);
        book.setIsbn(requestDto.isbn());
        book.setPrice(requestDto.price());
        book.setDescription(requestDto.description());
        book.setCoverImage(requestDto.coverImage());
        
        BookResponseDto expected = new BookResponseDto();
        expected.setId(bookId);
        expected.setTitle(requestDto.title());
        expected.setAuthor(requestDto.author());
        expected.setCategoriesIds(requestDto.categoriesIds());
        expected.setIsbn(requestDto.isbn());
        expected.setPrice(requestDto.price());
        expected.setDescription(requestDto.description());
        expected.setCoverImage(requestDto.coverImage());
        
        when(bookMapper.toBook(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expected);
        
        //When
        BookResponseDto actual = bookService.save(requestDto);
        
        //Then
        assertEquals(actual, expected);
        verify(bookRepository, times(1)).save(book);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }
    
    @Test
    @DisplayName("""
            Verify findById() method works properly
            and info about book with existed id was return""")
    public void findById_ValidId_ReturnsValidBookResponseDto() {
        //Given
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("title");
        book.setAuthor("author");
        book.setIsbn("1234567890");
        book.setPrice(BigDecimal.valueOf(300));
        book.setDescription("description");
        book.setCoverImage("link");
        
        BookResponseDto expected = new BookResponseDto();
        expected.setId(bookId);
        expected.setTitle(book.getTitle());
        expected.setAuthor(book.getAuthor());
        expected.setIsbn(book.getIsbn());
        expected.setPrice(book.getPrice());
        expected.setDescription(book.getDescription());
        expected.setCoverImage(book.getCoverImage());
        
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(expected);
        
        //When
        BookResponseDto actual = bookService.findById(bookId);
        
        //Then
        assertEquals(expected, actual);
        verify(bookRepository, times(1)).findById(bookId);
        verifyNoMoreInteractions(bookRepository, bookMapper);
        
    }
    
    @Test
    @DisplayName("""
            Verity findById() works properly and throw an exception when book id is invalid""")
    public void findById_InvalidId_ThrowsException() {
        // Given
        Long bookId = 13L;
        
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        
        //When
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> bookService.findById(bookId)
        );
        String expected = "Can't find book by id: %d".formatted(bookId);
        String actual = exception.getMessage();
        
        //Then
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("""
            Verity findALL() method works and info about all books that exists are
              returned in BookResponseDtos""")
    public void findAll_ValidPageable_ReturnsAllBooks() {
        //Given
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("title");
        book1.setAuthor("author");
        book1.setIsbn("1234567890");
        book1.setPrice(BigDecimal.valueOf(300));
        book1.setDescription("description");
        book1.setCoverImage("link");
        
        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("title 2");
        book2.setAuthor("author 2");
        book2.setIsbn("1234567892");
        book2.setPrice(BigDecimal.valueOf(200));
        book2.setDescription("description 2");
        book2.setCoverImage("link 2");
        
        BookResponseDto expectedDto1 = new BookResponseDto();
        expectedDto1.setId(book1.getId());
        expectedDto1.setTitle(book1.getTitle());
        expectedDto1.setAuthor(book1.getAuthor());
        expectedDto1.setIsbn(book1.getIsbn());
        expectedDto1.setPrice(book1.getPrice());
        expectedDto1.setDescription(book1.getDescription());
        expectedDto1.setCoverImage(book1.getCoverImage());
        
        BookResponseDto expectedDto2 = new BookResponseDto();
        expectedDto2.setId(book2.getId());
        expectedDto2.setTitle(book2.getTitle());
        expectedDto2.setAuthor(book2.getAuthor());
        expectedDto2.setIsbn(book2.getIsbn());
        expectedDto2.setPrice(book2.getPrice());
        expectedDto2.setDescription(book2.getDescription());
        expectedDto2.setCoverImage(book2.getCoverImage());
        
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> books = new PageImpl<>(List.of(book1, book2));
        
        when(bookRepository.findAll(pageable)).thenReturn(books);
        when(bookMapper.toDto(book1)).thenReturn(expectedDto1);
        when(bookMapper.toDto(book2)).thenReturn(expectedDto2);
        
        //When
        List<BookResponseDto> expected = List.of(expectedDto1, expectedDto2);
        List<BookResponseDto> actual = bookService.findAll(pageable);
        
        //Then
        assertEquals(expected, actual);
        verify(bookRepository, times(1)).findAll(pageable);
    }
    
    @Test
    @DisplayName("""
            Verify updateById() methods works and information about book with
            specify id was updated when book exists.
            """)
    public void updateById_ValidBookId_ReturnsValidBookResponseDto() {
        //Given
        BookRequestDto requestDto = new BookRequestDto(
                "test book",
                "test author",
                Set.of(1L, 2L),
                "1234567891",
                BigDecimal.valueOf(400),
                "Test description",
                "test link");
        
        Long bookId = 1L;
        Set<Category> categories = requestDto.categoriesIds().stream()
                .map(id -> {
                    Category category = new Category();
                    category.setId(id);
                    return category;
                })
                .collect(Collectors.toSet());
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("title");
        book.setAuthor("author");
        book.setCategories(categories);
        book.setIsbn("1234567890");
        book.setPrice(BigDecimal.valueOf(300));
        book.setDescription("description");
        book.setCoverImage("link");
        
        BookResponseDto expected = new BookResponseDto();
        expected.setId(bookId);
        expected.setTitle(requestDto.title());
        expected.setAuthor(requestDto.author());
        expected.setCategoriesIds(requestDto.categoriesIds());
        expected.setIsbn(requestDto.isbn());
        expected.setPrice(requestDto.price());
        expected.setDescription(requestDto.description());
        expected.setCoverImage(requestDto.coverImage());
        
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expected);
        
        //When
        BookResponseDto actual = bookService.updateById(bookId, requestDto);
        
        //Then
        assertEquals(expected, actual);
        verify(bookRepository, Mockito.times(1)).save(book);
    }
    
    @Test
    @DisplayName("""
            Verify deleteById() method works properly and delete book
            with specified valid id""")
    public void deleteById_ValidId_DeletesBook() {
        //Given
        Long bookId = anyLong();
        
        when(bookRepository.existsById(bookId)).thenReturn(true);
        
        //When
        bookService.deleteById(bookId);
        
        //Then
        verify(bookRepository, times(1)).deleteById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }
    
    @Test
    @DisplayName("""
            Verify deleteById() methods works properly and throws an exception
            when book's id doesn't exist""")
    public void deleteById_InvalidId_ThrowsException() {
        //Given
        Long bookId = -1L;
        
        when(bookRepository.existsById(bookId)).thenReturn(false);
        
        //When
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> bookService.deleteById(bookId)
        );
        
        String expected = "Entity with id: %d doesn't exist".formatted(bookId);
        String actual = exception.getMessage();
        
        //Then
        assertEquals(expected, actual);
        verify(bookRepository, times(1)).existsById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }
    
    @Test
    @DisplayName("""
            Verify findBooksByCategoryId() method works properly
            and returns a list of BookResponseDtoWithoutCategoryIds """)
    public void findBooksByCategoryId_ValidId_ReturnsListOfBookResponseDtoWithoutCategoryIds() {
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
        
        BookResponseDtoWithoutCategoryIds expectedDto1 = new BookResponseDtoWithoutCategoryIds(
                book1.getId(),
                book1.getTitle(),
                book1.getAuthor(),
                book1.getIsbn(),
                book1.getPrice(),
                book1.getDescription(),
                book1.getCoverImage()
        );
        
        BookResponseDtoWithoutCategoryIds expectedDto2 = new BookResponseDtoWithoutCategoryIds(
                book2.getId(),
                book2.getTitle(),
                book2.getAuthor(),
                book2.getIsbn(),
                book2.getPrice(),
                book2.getDescription(),
                book2.getCoverImage()
        );
        
        when(categoryRepository.existsById(category1Id)).thenReturn(true);
        when(bookRepository.findAllByCategoriesId(category1Id)).thenReturn(List.of(book1, book2));
        when(bookMapper.toDtoWithoutCategories(book1)).thenReturn(expectedDto1);
        when(bookMapper.toDtoWithoutCategories(book2)).thenReturn(expectedDto2);
        
        //When
        List<BookResponseDtoWithoutCategoryIds> expected = List.of(expectedDto1, expectedDto2);
        List<BookResponseDtoWithoutCategoryIds> actual =
                bookService.findBooksByCategoryId(category1Id);
        
        //Then
        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).existsById(category1Id);
        verify(bookRepository, times(1)).findAllByCategoriesId(category1Id);
        verifyNoMoreInteractions(categoryRepository, bookRepository, bookMapper);
    }
    
    @Test
    @DisplayName("""
            Verify findBooksByCategoryId() method works properly
            and throws an exception when category's id is invalid""")
    public void findBooksByCategoryId_InvalidId_ThrowsException() {
        //Given
        Long invalidId = 100L;
        
        when(categoryRepository.existsById(invalidId)).thenReturn(false);
        
        //When
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> bookService.findBooksByCategoryId(invalidId)
        );
        
        String expected = "Entity with id: %d doesn't exist".formatted(invalidId);
        String actual = exception.getMessage();
        
        //Then
        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).existsById(invalidId);
        verifyNoMoreInteractions(categoryRepository, bookRepository, bookMapper);
    }
}
