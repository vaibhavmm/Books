DELETE FROM books WHERE id =2;
INSERT INTO books(id,serial_no,name,genre,publisher_name,total_pages,timestamp)
values (2,'ISBN-978-91-7234','The Power of your subconsious mind','Non-fiction','FingerPrint Publishing',256,now());
DELETE FROM authors WHERE id =2;
INSERT INTO authors(id,first_name,middle_name,last_name,timestamp) values(2,'Dr.Joseph','','Murphy',now());
DELETE FROM books_and_authors WHERE book_id =2 AND author_id =2;
INSERT INTO books_and_authors(book_id, author_id) values (2,2);



