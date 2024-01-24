package yablunovskyi.bookstore.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yablunovskyi.bookstore.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @EntityGraph(attributePaths = "categories")
    Optional<Book> findById(Long id);
    
    @EntityGraph(attributePaths = "categories")
    Page<Book> findAll(Pageable pageable);
    
    @Query("select b from Book b join fetch b.categories c where c.id = :id")
    List<Book> findAllByCategoriesId(Long id);
}
