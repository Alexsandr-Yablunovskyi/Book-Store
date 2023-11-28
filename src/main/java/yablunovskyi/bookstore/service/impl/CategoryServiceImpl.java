package yablunovskyi.bookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yablunovskyi.bookstore.dto.category.CategoryRequestDto;
import yablunovskyi.bookstore.dto.category.CategoryResponseDto;
import yablunovskyi.bookstore.exception.EntityNotFoundException;
import yablunovskyi.bookstore.mapper.CategoryMapper;
import yablunovskyi.bookstore.model.Category;
import yablunovskyi.bookstore.repository.CategoryRepository;
import yablunovskyi.bookstore.service.CategoryService;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    @Override
    public CategoryResponseDto save(CategoryRequestDto requestDto) {
        Category category = categoryMapper.toCategory(requestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }
    
    @Override
    public CategoryResponseDto findById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id: %d".formatted(id))
        ));
    }
    
    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }
    
    @Override
    public CategoryResponseDto updateById(Long id, CategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id: %d".formatted(id))
        );
        categoryMapper.updateCategory(requestDto, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }
    
    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity with id: %d doesn't exist".formatted(id));
        }
        categoryRepository.deleteById(id);
    }
}
