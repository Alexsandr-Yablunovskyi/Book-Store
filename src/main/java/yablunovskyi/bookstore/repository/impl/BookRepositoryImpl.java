package yablunovskyi.bookstore.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yablunovskyi.bookstore.exception.DataProcessingException;
import yablunovskyi.bookstore.model.Book;
import yablunovskyi.bookstore.repository.BookRepository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final EntityManagerFactory entityManagerFactory;
    
    @Override
    public Book save(Book book) {
        EntityTransaction transaction = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(book);
            transaction.commit();
            return book;
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't creat a new book", e);
        }
    }
    
    @Override
    public List<Book> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery("FROM Book", Book.class).getResultList();
        } catch (RuntimeException e) {
            throw new DataProcessingException("Can't get all books from DB", e);
        }
    }
}
