CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    name TEXT,
    email TEXT UNIQUE,
    password TEXT
);

CREATE TABLE halls (
    id SERIAL PRIMARY KEY,
    row INT NOT NULL,
    seat INT NOT NULL,
    email TEXT  NOT NULL REFERENCES accounts(email),
    UNIQUE (row, seat)
);