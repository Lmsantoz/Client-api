CREATE TABLE clients (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    cpf varchar(11) NOT NULL UNIQUE,
    birth_date DATE NOT NULL
)