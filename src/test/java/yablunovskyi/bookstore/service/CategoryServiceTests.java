package yablunovskyi.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import yablunovskyi.bookstore.dto.category.CategoryRequestDto;
import yablunovskyi.bookstore.dto.category.CategoryResponseDto;
import yablunovskyi.bookstore.mapper.CategoryMapper;
import yablunovskyi.bookstore.model.Category;
import yablunovskyi.bookstore.repository.CategoryRepository;
import yablunovskyi.bookstore.service.impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {
    @Mock
    private CategoryRepository categoryRepository;
    
    @Mock
    private CategoryMapper categoryMapper;
    
    @InjectMocks
    private CategoryServiceImpl categoryService;
    
    @Test
    @DisplayName("""
            Verify save() method works and saves categories""")
    public void save_ValidCategoryRequestDto_ReturnsValidResponse() {
        //Given
        Category category = new Category();
        category.setId(1L);
        category.setName("test name");
        category.setDescription("test description");
        
        CategoryResponseDto expected = new CategoryResponseDto(category.getId(),
                category.getName(),
                category.getDescription()
        );
        
        CategoryRequestDto requestDto = new CategoryRequestDto(
                "test name",
                "test description"
        );
        
        when(categoryMapper.toCategory(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expected);
        
        //When
        CategoryResponseDto actual = categoryService.save(requestDto);
        
        //Then
        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).save(category);
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }
    
    @Test
    @DisplayName("""
            Verify findById() method works properly and returns valid CategoryResponseDto""")
    public void findById_ValidId_ReturnsValidResponse() {
        //Given
        Long categoryId = anyLong();
        
        Category category = new Category();
        category.setId(categoryId);
        category.setName("test name");
        category.setDescription("test description");
        
        CategoryResponseDto expected = new CategoryResponseDto(
                categoryId,
                category.getName(),
                category.getDescription()
        );
        
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(expected);
        
        //When
        CategoryResponseDto actual = categoryService.findById(categoryId);
        
        //Then
        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).findById(categoryId);
    }
    
    @Test
    @DisplayName("""
            Verify findById() method works properly and throws EntityNotFoundException""")
    public void findById_InvalidId_ThrowsException() {
        //Given
        Long categoryId = anyLong();
        
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        
        //When
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> categoryService.findById(categoryId)
        );
        String expected = "Can't find category by id: %d".formatted(categoryId);
        String actual = exception.getMessage();
        
        //Then
        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).findById(categoryId);
    }
    
    @Test
    @DisplayName("""
            Verify findAll() method works properly and return list of valid response dto""")
    public void findAll_ValidPageable_ReturnsAllCategories() {
        //Given
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("test 1 name");
        category1.setDescription("test 1 description");
        
        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("test 2 name");
        category2.setDescription("test 2 description");
        
        CategoryResponseDto responseDto1 = new CategoryResponseDto(
                category1.getId(),
                category1.getName(),
                category1.getDescription()
        );
        
        CategoryResponseDto responseDto2 = new CategoryResponseDto(
                category2.getId(),
                category2.getName(),
                category2.getDescription()
        );
        
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categories = new PageImpl<>(List.of(category1, category2));
        
        when(categoryMapper.toDto(category1)).thenReturn(responseDto1);
        when(categoryMapper.toDto(category2)).thenReturn(responseDto2);
        when(categoryRepository.findAll(pageable)).thenReturn(categories);
        
        //When
        List<CategoryResponseDto> expected = List.of(responseDto1, responseDto2);
        List<CategoryResponseDto> actual = categoryService.findAll(pageable);
        
        //Then
        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).findAll(pageable);
    }
    
    @Test
    @DisplayName("""
            Verify updateById() method works properly
            and updates info in category with specified id""")
    public void updateById_ValidId_ReturnsUpdatedValidResponse() {
        //Given
        Long categoryId = anyLong();
        
        CategoryRequestDto requestDto = new CategoryRequestDto(
                "test name",
                "test description"
        );
        
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Previous name");
        
        CategoryResponseDto expected = new CategoryResponseDto(
                categoryId,
                requestDto.name(),
                requestDto.description()
        );
        
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expected);
        
        //When
        CategoryResponseDto actual = categoryService.updateById(categoryId, requestDto);
        
        //Then
        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).save(category);
    }
    
    @Test
    @DisplayName("""
            Verify updateById() method works properly and throws an exception
            when category id is invalid""")
    public void updateById_InvalidId_ThrowsException() {
        //Given
        Long categoryId = anyLong();
        
        CategoryRequestDto requestDto = new CategoryRequestDto(
                "test name",
                "test description"
        );
        
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        
        //When
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> categoryService.updateById(categoryId, requestDto)
        );
        
        String expected = "Can't find category by id: %d".formatted(categoryId);
        String actual = exception.getMessage();
        
        //Then
        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).findById(categoryId);
    }
    
    @Test
    @DisplayName("""
            Verify deleteById() method works properly and deleted an category when
            category id is valid""")
    public void deleteById_ValidId_DeletesCategory() {
        //Given
        Long categoryId = anyLong();
        
        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        
        //When
        categoryService.deleteById(categoryId);
        
        //Then
        verify(categoryRepository, times(1)).deleteById(categoryId);
        verifyNoMoreInteractions(categoryRepository);
    }
    
    @Test
    @DisplayName("""
            Verify deleteById() method works properly and throws EntityNotFoundException
             when category id is invalid""")
    public void deleteById_InvalidId_ThrowsException() {
        //Given
        Long categoryId = anyLong();
        
        when(categoryRepository.existsById(categoryId)).thenReturn(false);
        
        //When
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> categoryService.deleteById(categoryId)
        );
        String expected = "Entity with id: %d doesn't exist".formatted(categoryId);
        String actual = exception.getMessage();
        
        //Then
        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, times(0)).deleteById(categoryId);
    }
}
