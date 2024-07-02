CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    email VARCHAR(100),
    logradouro VARCHAR(255),
    bairro VARCHAR(100),
    cep VARCHAR(20),
    cidade VARCHAR(100),
    uf VARCHAR(2),
    complemento VARCHAR(255),
    numero VARCHAR(20)
);

