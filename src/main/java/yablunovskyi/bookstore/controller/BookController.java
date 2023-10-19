package yablunovskyi.bookstore.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = "/api/books")
public class BookController {
    private final BookService bookService;
    
    @GetMapping
    public List<BookDto> findAll() {
        return bookService.findAll();
    }
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public BookDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookDto save(@RequestBody CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public BookDto updateById(@PathVariable Long id, @RequestBody CreateBookRequestDto requestDto) {
        return bookService.updateById(id, requestDto);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
