-- Criação da tabela User
CREATE TABLE IF NOT EXISTS User (
    Id INTEGER PRIMARY KEY AUTOINCREMENT,
    Username TEXT NOT NULL UNIQUE,
    PasswordHash BLOB NOT NULL,
    PasswordSalt BLOB NOT NULL
);

-- Exemplo de inserção de usuário (com hash e salt fictícios)
-- Você pode remover ou alterar conforme necessário
/*
INSERT INTO User (Username, PasswordHash, PasswordSalt)
VALUES (
    'teste',
    X'9F86D081884C7D659A2FEAA0C55AD015A3BF4F1B2B0B822CD15D6C15B0F00A08', -- hash SHA-256 de "test"
    X'ABCDEF1234567890ABCDEF1234567890' -- salt fictício
);*/
