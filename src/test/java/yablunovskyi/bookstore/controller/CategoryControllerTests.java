package yablunovskyi.bookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;
import yablunovskyi.bookstore.dto.book.BookResponseDtoWithoutCategoryIds;
import yablunovskyi.bookstore.dto.category.CategoryRequestDto;
import yablunovskyi.bookstore.dto.category.CategoryResponseDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(
        scripts = "classpath:database/categories/add-default-categories.sql"
)
@Sql(
        scripts = "classpath:database/categories/delete-all-categories.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public class CategoryControllerTests {
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
        //teardown(dataSource);
    }
    
    /*@BeforeEach
    void beforeEach(@Autowired DataSource dataSource) {
        setUpCategories(dataSource);
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
    private static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/categories/delete-all-categories.sql")
            );
        }
    }*/
    
    @Test
    @DisplayName("""
            Verify createCategory() method works and create a new category in database""")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createCategory_ValidRequestDto_Success() throws Exception {
        //Given
        CategoryRequestDto requestDto = new CategoryRequestDto(
                "test name",
                "test description"
        );
        Long id = 100L;
        CategoryResponseDto expected = new CategoryResponseDto(
                id,
                requestDto.name(),
                requestDto.description()
        );
        
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        
        //When
        MvcResult result = mockMvc.perform(post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        //Then
        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class);
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
    
    @Test
    @DisplayName("""
            Verify findById() method works properly
            and returns book with specified id""")
    /*@Sql(
            scripts = "classpath:database/categories/add-default-categories.sql"
    )
    @Sql(
            scripts = "classpath:database/categories/delete-all-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )*/
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void findById_ValidId_ReturnsValidCategory() throws Exception {
        //Given
        CategoryResponseDto expected = new CategoryResponseDto(
                2L,
                "second test category",
                "second description"
        );
        
        //When
        MvcResult result = mockMvc.perform(get("/categories/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        //Then
        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("""
            Verify findAll() method works and returns all categories from the database""")
    /*@Sql(
            scripts = "classpath:database/categories/add-default-categories.sql"
    )
    @Sql(
            scripts = "classpath:database/categories/delete-all-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )*/
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void findAll_GivenCategoriesInDB_ReturnsAllCategories() throws Exception {
        //Given
        CategoryResponseDto responseDto1 = new CategoryResponseDto(
                1L,
                "first test category",
                "first description"
        );
        
        CategoryResponseDto responseDto2 = new CategoryResponseDto(
                2L,
                "second test category",
                "second description"
        );
        
        CategoryResponseDto responseDto3 = new CategoryResponseDto(
                3L,
                "third test category",
                "third description"
        );
        
        List<CategoryResponseDto> expected = new ArrayList<>();
        expected.add(responseDto1);
        expected.add(responseDto2);
        expected.add(responseDto3);
        
        //When
        MvcResult result = mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        //Then
        CategoryResponseDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                CategoryResponseDto[].class);
        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());
    }
    
    @Test
    @DisplayName("""
            Verify updateCategoryById() method works properly
            and returns updated CategoryResponseDto""")
    /*@Sql(
            scripts = "classpath:database/categories/add-default-categories.sql"
    )
    @Sql(
            scripts = "classpath:database/categories/delete-all-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )*/
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateCategoryById_ValidId_ReturnsValidDto() throws Exception {
        //Given
        Long categoryId = 3L;
        
        CategoryRequestDto requestDto = new CategoryRequestDto(
                "test name",
                "test description"
        );
        
        CategoryResponseDto expected = new CategoryResponseDto(
                categoryId,
                requestDto.name(),
                requestDto.description()
        );
        
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        
        //When
        MvcResult result = mockMvc.perform(put("/categories/3")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        //Then
        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class
        );
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("""
            Verify updateCategoryById() method works properly
            and forbids the USER from updating the category""")
    /*@Sql(
            scripts = "classpath:database/categories/add-default-categories.sql"
    )
    @Sql(
            scripts = "classpath:database/categories/delete-all-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )*/
    @WithMockUser
    public void updateCategoryById_InvalidAccessRole_ReturnsValidDto() throws Exception {
        //Given
        CategoryRequestDto requestDto = new CategoryRequestDto(
                "test name",
                "test description"
        );
        
        CategoryResponseDto expected = new CategoryResponseDto(
                3L,
                "third test category",
                "third description"
        );
        
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        
        //When
        mockMvc.perform(put("/categories/3")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
        
        MvcResult result = mockMvc.perform(get("/categories/3")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        //Then
        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class
        );
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("""
            Verify delete() method works properly
            and delete category with specified id from database""")
    /*@Sql(
            scripts = "classpath:database/categories/add-default-categories.sql"
    )
    @Sql(
            scripts = "classpath:database/categories/delete-all-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )*/
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void delete_ValidId_Success() throws Exception {
        //Given
        //When
        mockMvc.perform(delete("/categories/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
        
        //Then
        MvcResult result = mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        CategoryResponseDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                CategoryResponseDto[].class);
        Assertions.assertEquals(2, actual.length);
    }
    
    @Test
    @DisplayName("""
            Verify delete() method works properly
            and forbids the USER from deleting the category""")
    /*@Sql(
            scripts = "classpath:database/categories/add-default-categories.sql"
    )
    @Sql(
            scripts = "classpath:database/categories/delete-all-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )*/
    @WithMockUser
    public void delete_InvalidAccessRole_ReturnForbiddenStatus() throws Exception {
        //Given
        CategoryResponseDto expected = new CategoryResponseDto(
                3L,
                "third test category",
                "third description"
        );
        //When
        mockMvc.perform(delete("/categories/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
        //Then
        MvcResult result = mockMvc.perform(get("/categories/3"))
                .andExpect(status().isOk())
                .andReturn();
        
        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class
        );
        
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("""
            Verify findsBookByCategoryId() method works and returns all books with
             specified category from the database""")
    @Sql(
            scripts = "classpath:database/books/add-default-books.sql"
    )
    @Sql(
            scripts = "classpath:database/books/delete-all-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void findBooksByCategoryId_GivenBooksAndCategoriesInDB_ReturnsValidDto()
            throws Exception {
        //Given
        BookResponseDtoWithoutCategoryIds responseDto1 = new BookResponseDtoWithoutCategoryIds(
                1L,
                "first test title",
                "first test author",
                "1234567890",
                BigDecimal.valueOf(100),
                "1 description",
                "1 cover image"
        );
        
        BookResponseDtoWithoutCategoryIds responseDto2 = new BookResponseDtoWithoutCategoryIds(
                2L,
                "second test title",
                "second test author",
                "2234567890",
                BigDecimal.valueOf(200),
                "2 description",
                "2 cover image"
        );
        List<BookResponseDtoWithoutCategoryIds> expected = new ArrayList<>();
        expected.add(responseDto1);
        expected.add(responseDto2);
        
        //When
        MvcResult result = mockMvc.perform(get("/categories/2/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        //Then
        BookResponseDtoWithoutCategoryIds[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                BookResponseDtoWithoutCategoryIds[].class);
        Assertions.assertEquals(2, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());
    }
}
