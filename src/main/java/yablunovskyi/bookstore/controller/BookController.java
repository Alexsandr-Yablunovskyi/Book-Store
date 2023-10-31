package yablunovskyi.bookstore.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<BookDto> findAll() {
        return bookService.findAll();
    }
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public BookDto findById(@PathVariable @Positive Long id) {
        return bookService.findById(id);
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookDto save(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public BookDto updateById(
            @PathVariable @Positive Long id, @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.updateById(id, requestDto);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive Long id) {
        bookService.deleteById(id);
    }
}
