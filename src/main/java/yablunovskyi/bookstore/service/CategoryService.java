package yablunovskyi.bookstore.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import yablunovskyi.bookstore.dto.category.CategoryRequestDto;
import yablunovskyi.bookstore.dto.category.CategoryResponseDto;

public interface CategoryService {
    CategoryResponseDto findById(Long id);
    
    List<CategoryResponseDto> findAll(Pageable pageable);
    
    CategoryResponseDto save(CategoryRequestDto requestDto);
    
    CategoryResponseDto updateById(Long id, CategoryRequestDto requestDto);
    
    void deleteById(Long id);
}
