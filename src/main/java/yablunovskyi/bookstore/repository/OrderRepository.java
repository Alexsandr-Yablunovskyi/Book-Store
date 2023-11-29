package yablunovskyi.bookstore.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yablunovskyi.bookstore.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems", "user", "orderItems.book"})
    List<Order> findByUserId(Long id, Pageable pageable);
    
    @EntityGraph(attributePaths = "orderItems")
    Optional<Order> findByUserIdAndId(Long userId, Long id);
}
