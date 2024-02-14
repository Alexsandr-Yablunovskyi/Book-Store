INSERT INTO books (id, title, author, isbn, price, description, cover_image)
VALUES (1, "first test title", "first test author", "1234567890", 100, "1 description", "1 cover image"),
       (2, "second test title", "second test author", "2234567890", 200, "2 description", "2 cover image"),
       (3, "third test title", "third test author", "3234567890", 300, "3 description", "3 cover image");

INSERT INTO books_categories(book_id, category_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 1);
