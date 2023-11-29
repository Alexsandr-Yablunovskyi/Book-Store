package yablunovskyi.bookstore.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yablunovskyi.bookstore.model.CartItem;
import yablunovskyi.bookstore.model.ShoppingCart;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndShoppingCartUserEmail(Long id, String email);
    
    @EntityGraph(attributePaths = "book")
    Optional<CartItem> findByShoppingCartAndBookId(ShoppingCart cart, Long bookId);
}
