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
import yablunovskyi.bookstore.dto.BookDto;
import yablunovskyi.bookstore.dto.CreateBookRequestDto;
import yablunovskyi.bookstore.service.BookService;

@Tag(name = "Book management", description = "Endpoints to managing books")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all books",
            description = "Get a list of all available books from the datase")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<BookDto> findAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a book by id",
            description = "Get a book by id from the database")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public BookDto findById(@PathVariable @Positive Long id) {
        return bookService.findById(id);
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save a book in the database",
            description = "Save a valid information about book in the database")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public BookDto save(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a book by id",
            description = "Update a book by id with a valid information in the database")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public BookDto updateById(
            @PathVariable @Positive Long id, @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.updateById(id, requestDto);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a book by id",
            description = "Delete a book by id from the database")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive Long id) {
        bookService.deleteById(id);
    }
}
