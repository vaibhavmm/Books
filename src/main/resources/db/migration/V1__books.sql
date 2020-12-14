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
