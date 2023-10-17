package yablunovskyi.bookstore.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yablunovskyi.bookstore.dto.BookDto;
import yablunovskyi.bookstore.dto.CreateBookRequestDto;
import yablunovskyi.bookstore.service.BookService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;
    
    @GetMapping
    public List<BookDto> findAll() {
        return bookService.findAll();
    }
    
    @PostMapping
    public BookDto save(@RequestBody CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }
    
}
