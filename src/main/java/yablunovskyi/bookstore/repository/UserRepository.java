package yablunovskyi.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yablunovskyi.bookstore.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
