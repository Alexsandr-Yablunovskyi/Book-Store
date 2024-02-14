package yablunovskyi.bookstore.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yablunovskyi.bookstore.dto.book.BookRequestDto;
import yablunovskyi.bookstore.dto.book.BookResponseDto;
import yablunovskyi.bookstore.dto.book.BookResponseDtoWithoutCategoryIds;
import yablunovskyi.bookstore.exception.EntityNotFoundException;
import yablunovskyi.bookstore.mapper.BookMapper;
import yablunovskyi.bookstore.model.Book;
import yablunovskyi.bookstore.model.Category;
import yablunovskyi.bookstore.repository.BookRepository;
import yablunovskyi.bookstore.repository.CategoryRepository;
import yablunovskyi.bookstore.service.BookService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    
    @Override
    public BookResponseDto save(BookRequestDto requestDto) {
        Book book = bookMapper.toBook(requestDto);
        book.setCategories(categoriesIdToCategories(requestDto.categoriesIds()));
        return bookMapper.toDto(bookRepository.save(book));
    }
    
    @Override
    public BookResponseDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: %d".formatted(id)))
        );
    }
    
    @Override
    public List<BookResponseDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }
    
    @Override
    public BookResponseDto updateById(Long id, BookRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find a book by id: " + id)
        );
        bookMapper.updateRequestDtoToBook(requestDto, book);
        book.setCategories(categoriesIdToCategories(requestDto.categoriesIds()));
        return bookMapper.toDto(bookRepository.save(book));
    }
    
    @Override
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity with id: %d doesn't exist".formatted(id));
        }
        bookRepository.deleteById(id);
    }
    
    @Override
    public List<BookResponseDtoWithoutCategoryIds> findBooksByCategoryId(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity with id: %d doesn't exist".formatted(id));
        }
        return bookRepository.findAllByCategoriesId(id).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }
    
    private Set<Category> categoriesIdToCategories(Set<Long> categoriesId) {
        return categoriesId.stream()
                .map(categoryRepository::getReferenceById)
                .collect(Collectors.toSet());
    }
}
