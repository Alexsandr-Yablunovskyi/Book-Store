package yablunovskyi.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yablunovskyi.bookstore.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
