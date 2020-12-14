CREATE TABLE IF NOT EXISTS books(
id INT PRIMARY KEY,
serial_no TEXT,
name TEXT,
genre TEXT,
publisher_name TEXT,
total_pages INT,
timestamp TIMESTAMP
);
CREATE TABLE IF NOT EXISTS authors(
id INT PRIMARY KEY,
first_name TEXT,
middle_name TEXT,
last_name TEXT,
timestamp TIMESTAMP
);
CREATE TABLE IF NOT EXISTS books_and_authors(
id SERIAL PRIMARY KEY,
book_id INT,
author_id INT
);
DELETE FROM books WHERE id =1;
INSERT INTO books(id,serial_no,name,genre,publisher_name,total_pages,timestamp)
values (1,'ISBN-978-91-7599','How to Win Friends & Influence People','Motivational','FingerPrint Pubishing',278,now());
DELETE FROM authors WHERE id =1;
INSERT INTO authors(id,first_name,middle_name,last_name,timestamp) values(1,'Dale','','Carnegie',now());
DELETE FROM books_and_authors WHERE book_id =1 AND author_id =1;
INSERT INTO books_and_authors(book_id, author_id) values (1,1);