package yablunovskyi.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import yablunovskyi.bookstore.dto.book.BookResponseDtoWithoutCategoryIds;
import yablunovskyi.bookstore.dto.category.CategoryRequestDto;
import yablunovskyi.bookstore.dto.category.CategoryResponseDto;
import yablunovskyi.bookstore.service.BookService;
import yablunovskyi.bookstore.service.CategoryService;

@Tag(name = "Category management", description = "Endpoints to managing categories")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/categories")
public class CategoryController {
    private final BookService bookService;
    private final CategoryService categoryService;
    
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new category in the database",
            description = "Save a valid information about new category in the database")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CategoryResponseDto createCategory(@RequestBody @Valid CategoryRequestDto requestDto) {
        return categoryService.save(requestDto);
    }
    
    @Operation(summary = "Get a category by id",
            description = "Get a valid category by id from the database")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public CategoryResponseDto findById(@PathVariable @Positive Long id) {
        return categoryService.findById(id);
    }
    
    @Operation(summary = "Get all categories from the database",
            description = "Get a list of all available categories from the database")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }
    
    @Operation(summary = "Update a category by id",
            description = "Update a category by id with a valid information in the database")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CategoryResponseDto updateCategoryById(
            @PathVariable @Positive Long id,
            @RequestBody @Valid CategoryRequestDto requestDto) {
        return categoryService.updateById(id, requestDto);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a category by id",
            description = "Delete a category by if from the database")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive Long id) {
        categoryService.deleteById(id);
    }
    
    @Operation(summary = "Get all books by category id",
            description = "Get all books by category id from the database")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/books")
    public List<BookResponseDtoWithoutCategoryIds> findBooksByCategoryId(
            @PathVariable @Positive Long id) {
        return bookService.findBooksByCategoryId(id);
    }
}
