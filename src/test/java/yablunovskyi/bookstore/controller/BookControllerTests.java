package yablunovskyi.bookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;
import yablunovskyi.bookstore.dto.book.BookRequestDto;
import yablunovskyi.bookstore.dto.book.BookResponseDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTests {
    protected static MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext,
            @Autowired DataSource dataSource) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        teardown(dataSource);
        setUpCategories(dataSource);
    }
    
    @BeforeEach
    void beforeEach(@Autowired DataSource dataSource) {
        setUpBooks(dataSource);
    }
    
    @AfterEach
    void afterEach(@Autowired DataSource dataSource) {
        teardown(dataSource);
    }
    
    @SneakyThrows
    private static void setUpCategories(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/categories/add-default-categories.sql")
            );
        }
    }
    
    @SneakyThrows
    private static void setUpBooks(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books/add-default-books.sql")
            );
        }
    }
    
    @SneakyThrows
    private static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books/delete-all-books.sql")
            );
        }
    }
    
    @Test
    @DisplayName("""
            Verify save() method works and save a new book to database""")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void save_ValidRequestDto_Success() throws Exception {
        //Given
        BookRequestDto requestDto = new BookRequestDto(
                "title",
                "author",
                Set.of(1L, 2L),
                "1234567800",
                BigDecimal.valueOf(400),
                "description",
                "link"
        );
        BookResponseDto expected = new BookResponseDto();
        expected.setTitle(requestDto.title());
        expected.setAuthor(requestDto.author());
        expected.setCategoriesIds(requestDto.categoriesIds());
        expected.setIsbn(requestDto.isbn());
        expected.setPrice(requestDto.price());
        expected.setDescription(requestDto.description());
        expected.setCoverImage(requestDto.coverImage());
        
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        
        //When
        MvcResult result = mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        //Then
        BookResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookResponseDto.class);
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
    
    @Test
    @DisplayName("""
            Verify findAll() method works and returns all books from the database""")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void findAll_GivenBooksInDB_ReturnsAllBooks() throws Exception {
        //Given
        BookResponseDto responseDto1 = new BookResponseDto();
        responseDto1.setId(1L);
        responseDto1.setTitle("first test title");
        responseDto1.setAuthor("first test author");
        responseDto1.setCategoriesIds(Set.of(1L, 2L));
        responseDto1.setIsbn("1234567890");
        responseDto1.setPrice(BigDecimal.valueOf(100));
        responseDto1.setDescription("1 description");
        responseDto1.setCoverImage("1 cover image");
        
        BookResponseDto responseDto2 = new BookResponseDto();
        responseDto2.setId(2L);
        responseDto2.setTitle("second test title");
        responseDto2.setAuthor("second test author");
        responseDto2.setCategoriesIds(Set.of(2L));
        responseDto2.setIsbn("2234567890");
        responseDto2.setPrice(BigDecimal.valueOf(200));
        responseDto2.setDescription("2 description");
        responseDto2.setCoverImage("2 cover image");
        
        BookResponseDto responseDto3 = new BookResponseDto();
        responseDto3.setId(3L);
        responseDto3.setTitle("third test title");
        responseDto3.setAuthor("third test author");
        responseDto3.setCategoriesIds(Set.of(1L));
        responseDto3.setIsbn("3234567890");
        responseDto3.setPrice(BigDecimal.valueOf(300));
        responseDto3.setDescription("3 description");
        responseDto3.setCoverImage("3 cover image");
        
        List<BookResponseDto> expected = new ArrayList<>();
        expected.add(responseDto1);
        expected.add(responseDto2);
        expected.add(responseDto3);
        
        //When
        MvcResult result = mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        //Then
        BookResponseDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                BookResponseDto[].class);
        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());
    }
    
    @Test
    @DisplayName("""
            Verify findById() method works properly
            and returns book with specified id""")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void findById_ValidId_ReturnsValidBook() throws Exception {
        //Given
        BookResponseDto expected = new BookResponseDto();
        expected.setId(2L);
        expected.setTitle("second test title");
        expected.setAuthor("second test author");
        expected.setCategoriesIds(Set.of(2L));
        expected.setIsbn("2234567890");
        expected.setPrice(BigDecimal.valueOf(200));
        expected.setDescription("2 description");
        expected.setCoverImage("2 cover image");
        
        //When
        MvcResult result = mockMvc.perform(get("/books/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        //Then
        BookResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookResponseDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("""
            Verify updateById() method works properly
            and returns updated BookResponseDto""")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateById_ValidId_ReturnsValidDto() throws Exception {
        //Given
        Long bookId = 3L;
        BookRequestDto requestDto = new BookRequestDto(
                "test book",
                "test author",
                Set.of(1L, 2L),
                "1234567891",
                BigDecimal.valueOf(400),
                "Test description",
                "test link");
        
        BookResponseDto expected = new BookResponseDto();
        expected.setId(bookId);
        expected.setTitle(requestDto.title());
        expected.setAuthor(requestDto.author());
        expected.setCategoriesIds(requestDto.categoriesIds());
        expected.setIsbn(requestDto.isbn());
        expected.setPrice(requestDto.price());
        expected.setDescription(requestDto.description());
        expected.setCoverImage(requestDto.coverImage());
        
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        
        //When
        MvcResult result = mockMvc.perform(put("/books/3")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        //Then
        BookResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookResponseDto.class
        );
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("""
            Verify updateById() method works properly
            and forbids the USER from updating the book""")
    @WithMockUser
    public void updateById_InvalidAccessRole_ReturnsForbiddenStatus() throws Exception {
        //Given
        Long bookId = 3L;
        BookRequestDto requestDto = new BookRequestDto(
                "test book",
                "test author",
                Set.of(1L, 2L),
                "1234567891",
                BigDecimal.valueOf(400),
                "Test description",
                "test link");
        
        BookResponseDto expected = new BookResponseDto();
        expected.setId(bookId);
        expected.setTitle("third test title");
        expected.setAuthor("third test author");
        expected.setCategoriesIds(Set.of(1L));
        expected.setIsbn("3234567890");
        expected.setPrice(BigDecimal.valueOf(300));
        expected.setDescription("3 description");
        expected.setCoverImage("3 cover image");
        
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        
        //When
        mockMvc.perform(put("/books/3")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
        
        //Then
        MvcResult result = mockMvc.perform(get("/books/3"))
                .andExpect(status().isOk())
                .andReturn();
        
        BookResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookResponseDto.class
        );
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("""
            Verify delete() method works properly
            and delete book with specified id from database""")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void delete_ValidId_Success() throws Exception {
        //Given
        //When
        mockMvc.perform(delete("/books/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
        
        //Then
        /*mockMvc.perform(get("/books/2"))
                .andExpect(status().isNoContent())
                .andReturn();*/
        
        MvcResult result = mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        BookResponseDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                BookResponseDto[].class);
        Assertions.assertEquals(2, actual.length);
    }
    
    @Test
    @DisplayName("""
            Verify delete() method works properly
            and forbids the USER from deleting the book""")
    @WithMockUser
    public void delete_InvalidAccessRole_ReturnForbiddenStatus() throws Exception {
        //Given
        BookResponseDto expected = new BookResponseDto();
        expected.setId(2L);
        expected.setTitle("second test title");
        expected.setAuthor("second test author");
        expected.setCategoriesIds(Set.of(2L));
        expected.setIsbn("2234567890");
        expected.setPrice(BigDecimal.valueOf(200));
        expected.setDescription("2 description");
        expected.setCoverImage("2 cover image");
        //When
        mockMvc.perform(delete("/books/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
        //Then
        MvcResult result = mockMvc.perform(get("/books/2"))
                .andExpect(status().isOk())
                .andReturn();
        
        BookResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookResponseDto.class
        );
        
        Assertions.assertEquals(expected, actual);
    }
}
