INSERT INTO books (id, title, author, isbn, price, description, cover_image)
VALUES (1, "test title", "test author", "1234567890", 100, "test description", "test cover image");

INSERT INTO books_categories(book_id, category_id)
VALUES (1, 1);
