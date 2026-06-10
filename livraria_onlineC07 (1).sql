-- PROJETO DE BANCO DE DADOS - SEGUNDA ENTREGA
-- Sistema de Livraria Online
-- Disciplina: Banco de Dados - C07 L2
-- Monitor: Felipe Tagawa
-- Equipe:
--   Leonardo Fabricio Vieira Fernandes - 356
--   William Andrade Camilo             - 552
--   Gabriel Amilton Perroni Camargo    - 876


DROP DATABASE IF EXISTS livraria_online;
CREATE DATABASE livraria_online
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE livraria_online;


-- 1. CRIACAO DAS TABELAS (8 tabelas - acima do minimo de 5)


-- Tabela Cliente
CREATE TABLE Cliente (
    id_cliente       INT             NOT NULL AUTO_INCREMENT,
    nome             VARCHAR(150)    NOT NULL,
    cpf              CHAR(11)        NOT NULL,
    email            VARCHAR(100)    NOT NULL,
    telefone         VARCHAR(20),
    data_nascimento  DATE,
    data_cadastro    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ativo            TINYINT(1)      NOT NULL DEFAULT 1,
    CONSTRAINT pk_cliente       PRIMARY KEY (id_cliente),
    CONSTRAINT uk_cliente_cpf   UNIQUE (cpf),
    CONSTRAINT uk_cliente_email UNIQUE (email)
) ENGINE=InnoDB;

-- Tabela Endereco (relacionamento 1:1 com Cliente)
CREATE TABLE Endereco (
    id_endereco  INT          NOT NULL AUTO_INCREMENT,
    id_cliente   INT          NOT NULL,
    logradouro   VARCHAR(150) NOT NULL,
    numero       VARCHAR(10),
    complemento  VARCHAR(80),
    bairro       VARCHAR(80),
    cidade       VARCHAR(80)  NOT NULL,
    uf           CHAR(2)      NOT NULL,
    cep          CHAR(9)      NOT NULL,
    CONSTRAINT pk_endereco         PRIMARY KEY (id_endereco),
    CONSTRAINT uk_endereco_cliente UNIQUE (id_cliente),
    CONSTRAINT fk_endereco_cliente FOREIGN KEY (id_cliente)
        REFERENCES Cliente (id_cliente)
        ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabela Autor
CREATE TABLE Autor (
    id_autor         INT          NOT NULL AUTO_INCREMENT,
    nome             VARCHAR(150) NOT NULL,
    nacionalidade    VARCHAR(80),
    data_nascimento  DATE,
    biografia        TEXT,
    CONSTRAINT pk_autor PRIMARY KEY (id_autor)
) ENGINE=InnoDB;

-- Tabela Categoria
CREATE TABLE Categoria (
    id_categoria  INT         NOT NULL AUTO_INCREMENT,
    nome          VARCHAR(80) NOT NULL,
    descricao     TEXT,
    CONSTRAINT pk_categoria      PRIMARY KEY (id_categoria),
    CONSTRAINT uk_categoria_nome UNIQUE (nome)
) ENGINE=InnoDB;

-- Tabela Livro
CREATE TABLE Livro (
    id_livro        INT            NOT NULL AUTO_INCREMENT,
    titulo          VARCHAR(200)   NOT NULL,
    isbn            CHAR(13),
    preco           DECIMAL(10,2)  NOT NULL,
    estoque         INT            NOT NULL DEFAULT 0,
    ano_publicacao  SMALLINT,
    descricao       TEXT,
    disponivel      TINYINT(1)     NOT NULL DEFAULT 1,
    CONSTRAINT pk_livro        PRIMARY KEY (id_livro),
    CONSTRAINT uk_livro_isbn   UNIQUE (isbn),
    CONSTRAINT ck_livro_preco  CHECK (preco >= 0),
    CONSTRAINT ck_livro_estoque CHECK (estoque >= 0)
) ENGINE=InnoDB;

-- Tabela associativa Autor_Livro (N:M)
CREATE TABLE Autor_Livro (
    id_autor  INT NOT NULL,
    id_livro  INT NOT NULL,
    CONSTRAINT pk_autor_livro       PRIMARY KEY (id_autor, id_livro),
    CONSTRAINT fk_autor_livro_autor FOREIGN KEY (id_autor)
        REFERENCES Autor (id_autor)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_autor_livro_livro FOREIGN KEY (id_livro)
        REFERENCES Livro (id_livro)
        ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabela associativa Livro_Categoria (N:M)
CREATE TABLE Livro_Categoria (
    id_livro      INT NOT NULL,
    id_categoria  INT NOT NULL,
    CONSTRAINT pk_livro_categoria           PRIMARY KEY (id_livro, id_categoria),
    CONSTRAINT fk_livro_categoria_livro     FOREIGN KEY (id_livro)
        REFERENCES Livro (id_livro)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_livro_categoria_categoria FOREIGN KEY (id_categoria)
        REFERENCES Categoria (id_categoria)
        ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabela Pedido (1:N com Cliente)
CREATE TABLE Pedido (
    id_pedido    INT            NOT NULL AUTO_INCREMENT,
    id_cliente   INT            NOT NULL,
    data_pedido  DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status       ENUM('pendente','pago','enviado','entregue','cancelado')
                                NOT NULL DEFAULT 'pendente',
    total        DECIMAL(10,2)  NOT NULL DEFAULT 0.00,
    observacao   TEXT,
    CONSTRAINT pk_pedido         PRIMARY KEY (id_pedido),
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (id_cliente)
        REFERENCES Cliente (id_cliente)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

-- Tabela associativa Pedido_Livro (N:M com atributos)
CREATE TABLE Pedido_Livro (
    id_pedido        INT           NOT NULL,
    id_livro         INT           NOT NULL,
    quantidade       INT           NOT NULL DEFAULT 1,
    preco_unitario   DECIMAL(10,2) NOT NULL,
    CONSTRAINT pk_pedido_livro         PRIMARY KEY (id_pedido, id_livro),
    CONSTRAINT fk_pedido_livro_pedido  FOREIGN KEY (id_pedido)
        REFERENCES Pedido (id_pedido)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_pedido_livro_livro   FOREIGN KEY (id_livro)
        REFERENCES Livro (id_livro)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT ck_pedido_livro_qtd     CHECK (quantidade > 0),
    CONSTRAINT ck_pedido_livro_preco   CHECK (preco_unitario >= 0)
) ENGINE=InnoDB;


-- 2. INSERCAO DE REGISTROS (minimo 5 por tabela)

-- Clientes
INSERT INTO Cliente (nome, cpf, email, telefone, data_nascimento) VALUES
('Leonardo Fabricio Vieira Fernandes', '11122233344', 'leonardo@email.com',  '(35) 99876-1111', '2003-04-15'),
('William Andrade Camilo',             '22233344455', 'william@email.com',   '(35) 99876-2222', '2003-08-22'),
('Gabriel Amilton Perroni Camargo',    '33344455566', 'gabriel@email.com',   '(35) 99876-3333', '2002-11-30'),
('Ana Carolina Souza',                 '44455566677', 'ana.souza@email.com', '(35) 99876-4444', '1998-02-10'),
('Bruno Henrique Lima',                '55566677788', 'bruno.lima@email.com','(35) 99876-5555', '1995-06-25'),
('Carla Mendes Oliveira',              '66677788899', 'carla.m@email.com',   '(35) 99876-6666', '2000-01-12');

-- Enderecos (1:1 com Cliente)
INSERT INTO Endereco (id_cliente, logradouro, numero, complemento, bairro, cidade, uf, cep) VALUES
(1, 'Rua das Acacias',     '100', 'Apto 201', 'Centro',       'Santa Rita do Sapucai', 'MG', '37540000'),
(2, 'Avenida Brasil',      '250', NULL,       'Jardim Coller','Santa Rita do Sapucai', 'MG', '37540001'),
(3, 'Rua Sao Joao',        '45',  'Casa',     'Bela Vista',   'Pouso Alegre',          'MG', '37550010'),
(4, 'Rua das Flores',      '789', 'Bloco B',  'Vila Nova',    'Itajuba',               'MG', '37500020'),
(5, 'Alameda dos Pinheiros','321', NULL,      'Centro',       'Sao Paulo',             'SP', '01310100'),
(6, 'Rua das Palmeiras',   '67',  'Apto 502', 'Copacabana',   'Rio de Janeiro',        'RJ', '22070010');

-- Autores
INSERT INTO Autor (nome, nacionalidade, data_nascimento, biografia) VALUES
('Machado de Assis',     'Brasileira',  '1839-06-21', 'Considerado o maior escritor da literatura brasileira, fundador da Academia Brasileira de Letras.'),
('Clarice Lispector',    'Brasileira',  '1920-12-10', 'Romancista e contista, uma das principais escritoras brasileiras do seculo XX.'),
('George Orwell',        'Britanica',   '1903-06-25', 'Jornalista e escritor britanico, conhecido por obras de critica politica e social.'),
('J. R. R. Tolkien',     'Britanica',   '1892-01-03', 'Filologo e escritor, criador do universo da Terra Media.'),
('Gabriel Garcia Marquez','Colombiana', '1927-03-06', 'Escritor colombiano, premio Nobel de Literatura em 1982, expoente do realismo magico.'),
('Jorge Amado',          'Brasileira',  '1912-08-10', 'Romancista baiano, um dos autores brasileiros mais traduzidos no mundo.');

-- Categorias
INSERT INTO Categoria (nome, descricao) VALUES
('Romance',              'Obras de ficcao narrativa com foco em relacionamentos e desenvolvimento de personagens.'),
('Literatura Brasileira','Obras escritas por autores brasileiros que retratam aspectos culturais e historicos do Brasil.'),
('Ficcao Cientifica',    'Genero que explora temas como tecnologia, futuro, espaco e realidades alternativas.'),
('Fantasia',             'Obras com elementos magicos, mitologia e mundos imaginarios.'),
('Distopia',             'Narrativas ambientadas em sociedades opressivas ou em colapso.'),
('Realismo Magico',      'Genero literario que mistura elementos fantasticos em contextos realistas.');

-- Livros
INSERT INTO Livro (titulo, isbn, preco, estoque, ano_publicacao, descricao) VALUES
('Dom Casmurro',                  '9788535910663', 39.90, 25, 1899, 'Classico de Machado de Assis sobre ciume e ambiguidade narrativa.'),
('A Hora da Estrela',             '9788532530803', 32.50, 18, 1977, 'Ultima obra de Clarice Lispector, sobre Macabea, uma jovem nordestina no Rio.'),
('1984',                          '9788535914849', 45.00, 30, 1949, 'Romance distopico de George Orwell sobre um regime totalitario.'),
('O Senhor dos Aneis',            '9788578277109', 89.90, 15, 1954, 'Epico de fantasia de Tolkien ambientado na Terra Media.'),
('Cem Anos de Solidao',           '9788501012074', 54.90, 22, 1967, 'Obra-prima de Garcia Marquez, marco do realismo magico.'),
('Capitaes da Areia',             '9788535914207', 36.50, 20, 1937, 'Romance de Jorge Amado sobre meninos de rua em Salvador.'),
('A Revolucao dos Bichos',        '9788535909555', 28.90, 40, 1945, 'Fabula politica de George Orwell.');

-- Relacionamento Autor_Livro (N:M)
INSERT INTO Autor_Livro (id_autor, id_livro) VALUES
(1, 1),  -- Machado de Assis - Dom Casmurro
(2, 2),  -- Clarice Lispector - A Hora da Estrela
(3, 3),  -- Orwell - 1984
(4, 4),  -- Tolkien - O Senhor dos Aneis
(5, 5),  -- Garcia Marquez - Cem Anos de Solidao
(6, 6),  -- Jorge Amado - Capitaes da Areia
(3, 7);  -- Orwell - A Revolucao dos Bichos

-- Relacionamento Livro_Categoria (N:M)
INSERT INTO Livro_Categoria (id_livro, id_categoria) VALUES
(1, 1), (1, 2),  -- Dom Casmurro: Romance + Lit Brasileira
(2, 1), (2, 2),  -- A Hora da Estrela: Romance + Lit Brasileira
(3, 5), (3, 3),  -- 1984: Distopia + Ficcao Cientifica
(4, 4),          -- O Senhor dos Aneis: Fantasia
(5, 1), (5, 6),  -- Cem Anos: Romance + Realismo Magico
(6, 1), (6, 2),  -- Capitaes: Romance + Lit Brasileira
(7, 5);          -- A Revolucao dos Bichos: Distopia

-- Pedidos
INSERT INTO Pedido (id_cliente, status, total, observacao) VALUES
(1, 'entregue',  85.40,  'Entregar em horario comercial.'),
(2, 'pago',     134.80,  'Cliente pediu embalagem para presente.'),
(3, 'enviado',  121.40,  'Frete expresso solicitado.'),
(4, 'pendente',  45.00,  'Aguardando confirmacao de pagamento.'),
(5, 'cancelado', 89.90,  'Cancelado pelo cliente.'),
(1, 'pago',      69.40,  'Segundo pedido do cliente.');

-- Pedido_Livro (N:M com atributos)
INSERT INTO Pedido_Livro (id_pedido, id_livro, quantidade, preco_unitario) VALUES
(1, 1, 1, 39.90),
(1, 2, 1, 45.50),
(2, 3, 1, 45.00),
(2, 5, 1, 54.90),
(2, 7, 1, 34.90),
(3, 4, 1, 89.90),
(3, 6, 1, 31.50),
(4, 3, 1, 45.00),
(5, 4, 1, 89.90),
(6, 1, 1, 39.90),
(6, 7, 1, 29.50);


-- 3. CRIACAO DE USUARIOS E ROLES

-- Remove se ja existirem (para reexecucao do script)
DROP USER IF EXISTS 'leonardo_admin'@'localhost';
DROP USER IF EXISTS 'william_vendedor'@'localhost';
DROP ROLE IF EXISTS 'role_vendedor';

-- 2 usuarios arbitrarios
CREATE USER 'leonardo_admin'@'localhost'    IDENTIFIED BY 'Senha@Admin2025';
CREATE USER 'william_vendedor'@'localhost'  IDENTIFIED BY 'Senha@Vend2025';

-- 1 Role com pelo menos 2 privilegios
CREATE ROLE 'role_vendedor';

-- Privilegios para a role (mais de 2)
GRANT SELECT ON livraria_online.*          TO 'role_vendedor';
GRANT INSERT ON livraria_online.Pedido      TO 'role_vendedor';
GRANT INSERT ON livraria_online.Pedido_Livro TO 'role_vendedor';
GRANT UPDATE ON livraria_online.Pedido      TO 'role_vendedor';

-- Atribuindo a role aos usuarios por padrao
GRANT 'role_vendedor' TO 'leonardo_admin'@'localhost';
GRANT 'role_vendedor' TO 'william_vendedor'@'localhost';

-- Ativar role como padrao no login (sintaxe MySQL 8+)
SET DEFAULT ROLE 'role_vendedor' TO
    'leonardo_admin'@'localhost',
    'william_vendedor'@'localhost';

-- Privilegios adicionais somente para o admin
GRANT ALL PRIVILEGES ON livraria_online.* TO 'leonardo_admin'@'localhost' WITH GRANT OPTION;

FLUSH PRIVILEGES;



-- 4. OBJETOS PROGRAMAVEIS (4 distintos: VIEW, FUNCTION, PROCEDURE, TRIGGER)


-- 4.1 VIEW - Resumo de Pedidos com informacoes do cliente

DROP VIEW IF EXISTS vw_resumo_pedidos;

CREATE VIEW vw_resumo_pedidos AS
SELECT
    p.id_pedido,
    c.nome             AS cliente,
    c.email            AS email_cliente,
    p.data_pedido,
    p.status,
    COUNT(pl.id_livro) AS qtd_itens,
    SUM(pl.quantidade) AS total_unidades,
    p.total            AS valor_total
FROM Pedido p
INNER JOIN Cliente      c  ON c.id_cliente = p.id_cliente
LEFT  JOIN Pedido_Livro pl ON pl.id_pedido = p.id_pedido
GROUP BY p.id_pedido, c.nome, c.email, p.data_pedido, p.status, p.total;


-- 4.2 FUNCTION - Calcula o valor total real de um pedido

DROP FUNCTION IF EXISTS fn_calcula_total_pedido;

DELIMITER $$

CREATE FUNCTION fn_calcula_total_pedido(p_id_pedido INT)
RETURNS DECIMAL(10,2)
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE v_total DECIMAL(10,2);

    SELECT IFNULL(SUM(quantidade * preco_unitario), 0.00)
      INTO v_total
      FROM Pedido_Livro
     WHERE id_pedido = p_id_pedido;

    RETURN v_total;
END $$

DELIMITER ;



-- 4.3 PROCEDURE - Realiza venda: insere item no pedido e baixa estoque

DROP PROCEDURE IF EXISTS sp_realizar_venda;

DELIMITER $$

CREATE PROCEDURE sp_realizar_venda(
    IN  p_id_pedido   INT,
    IN  p_id_livro    INT,
    IN  p_quantidade  INT,
    OUT p_mensagem    VARCHAR(200)
)
BEGIN
    DECLARE v_estoque_atual INT;
    DECLARE v_preco         DECIMAL(10,2);
    DECLARE v_disponivel    TINYINT(1);

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_mensagem = 'Erro: a venda nao pode ser concluida.';
    END;

    START TRANSACTION;

    -- Verifica existencia e disponibilidade do livro
    SELECT estoque, preco, disponivel
      INTO v_estoque_atual, v_preco, v_disponivel
      FROM Livro
     WHERE id_livro = p_id_livro
     FOR UPDATE;

    IF v_disponivel = 0 THEN
        SET p_mensagem = 'Livro indisponivel para venda.';
        ROLLBACK;
    ELSEIF v_estoque_atual < p_quantidade THEN
        SET p_mensagem = CONCAT('Estoque insuficiente. Disponivel: ', v_estoque_atual);
        ROLLBACK;
    ELSE
        -- Insere item no pedido
        INSERT INTO Pedido_Livro (id_pedido, id_livro, quantidade, preco_unitario)
        VALUES (p_id_pedido, p_id_livro, p_quantidade, v_preco)
        ON DUPLICATE KEY UPDATE
            quantidade = quantidade + p_quantidade;

        -- Atualiza estoque
        UPDATE Livro
           SET estoque = estoque - p_quantidade
         WHERE id_livro = p_id_livro;

        -- Atualiza total do pedido com a funcao
        UPDATE Pedido
           SET total = fn_calcula_total_pedido(p_id_pedido)
         WHERE id_pedido = p_id_pedido;

        SET p_mensagem = 'Venda registrada com sucesso.';
        COMMIT;
    END IF;
END $$

DELIMITER ;


-- 4.4 TRIGGER - Atualiza total do pedido quando item e removido

DROP TRIGGER IF EXISTS trg_pedido_livro_after_delete;

DELIMITER $$

CREATE TRIGGER trg_pedido_livro_after_delete
AFTER DELETE ON Pedido_Livro
FOR EACH ROW
BEGIN
    -- Recoloca quantidade no estoque
    UPDATE Livro
       SET estoque = estoque + OLD.quantidade
     WHERE id_livro = OLD.id_livro;

    -- Recalcula total do pedido
    UPDATE Pedido
       SET total = fn_calcula_total_pedido(OLD.id_pedido)
     WHERE id_pedido = OLD.id_pedido;
END $$

DELIMITER ;

-- 4.5 TRIGGER - Impede pedido com livro indisponivel (BEFORE INSERT)

DROP TRIGGER IF EXISTS trg_pedido_livro_before_insert;

DELIMITER $$

CREATE TRIGGER trg_pedido_livro_before_insert
BEFORE INSERT ON Pedido_Livro
FOR EACH ROW
BEGIN
    DECLARE v_disponivel TINYINT(1);

    SELECT disponivel INTO v_disponivel
      FROM Livro
     WHERE id_livro = NEW.id_livro;

    IF v_disponivel = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Livro indisponivel: nao pode ser incluido em pedidos.';
    END IF;
END $$

DELIMITER ;

