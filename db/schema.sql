CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    name TEXT,
    phone VARCHAR(11) UNIQUE
);

CREATE TABLE halls (
    id SERIAL PRIMARY KEY,
    row INT NOT NULL,
    seat INT NOT NULL,
    phone VARCHAR(11)  NOT NULL REFERENCES accounts(phone),
    UNIQUE (row, seat)
);