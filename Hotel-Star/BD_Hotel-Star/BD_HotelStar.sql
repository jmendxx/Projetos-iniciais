DROP SCHEMA IF EXISTS `HotelStar`;
CREATE SCHEMA `HotelStar`;
USE `HotelStar`;

-- Tabela: Fornecedores
DROP TABLE IF EXISTS `Fornecedores`;
CREATE TABLE `Fornecedores` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `regSocial` VARCHAR(100),
  `cnpj` VARCHAR(20),
  `endereco` VARCHAR(100),
  `telefone` VARCHAR(20),
  `email` VARCHAR(50),
  `represLegal` VARCHAR(50),
  `cpf` VARCHAR(15),
  PRIMARY KEY (`id`)
);

INSERT INTO `Fornecedores` (regSocial, cnpj, endereco, telefone, email, represLegal, cpf) VALUES
('Alimentos Brasil', '12.345.678/0001-00', 'Rua A, 123', '31988889999', 'contato@alimbr.com', 'Paulo Mendes', '123.456.789-00'),
('Bebidas Minas', '98.765.432/0001-99', 'Rua B, 456', '31999998888', 'vendas@bebminas.com', 'Ana Clara', '234.567.890-11'),
('Limpeza Top', '11.222.333/0001-44', 'Av Central, 1000', '31987776655', 'atendimento@limtop.com', 'Carlos Silva', '345.678.901-22'),
('Hotelaria Luxo', '44.555.666/0001-77', 'Rua Luxo, 12', '31986665544', 'luxo@hotelaria.com', 'Fernanda Rocha', '456.789.012-33'),
('Papelaria Sol', '77.888.999/0001-55', 'Rua Papel, 321', '31985554433', 'papelaria@sol.com', 'Eduardo Lima', '567.890.123-44');

-- Tabela: Funcionários
DROP TABLE IF EXISTS `Funcionarios`;
CREATE TABLE `Funcionarios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50),
  `cpf` VARCHAR(15),
  `telefone` VARCHAR(20),
  `email` VARCHAR(50),
  `endereco` VARCHAR(100),
  `sexo` ENUM('Feminino', 'Masculino', 'Não binário', 'Outro'),
  `dataNascimento` DATE,
  `funcao` VARCHAR(30),
  PRIMARY KEY (`id`)
);

INSERT INTO `Funcionarios` (nome, cpf, telefone, email, endereco, sexo, dataNascimento, funcao) VALUES
('Teste', '123.123.123-13', '31985236475', 'teste@gmail.com', 'Rua zero, Centro', 'Outro', '1997-11-07', 'CEO'),
('Thais Cardoso', '121.231.239-99', '31988722343', 'thais@gmail.com', 'Rua Um, Centro', 'Feminino', '1995-04-12', 'TI'),
('Rafael Souza', '132.456.789-10', '31999887766', 'rafael@gmail.com', 'Rua Dois, Bairro Feliz', 'Masculino', '1990-06-25', 'Recepcionista'),
('Juliana Mendes', '145.789.321-22', '31988774455', 'juliana@gmail.com', 'Av Sete, N° 700', 'Feminino', '1988-10-03', 'Gerente'),
('Carlos Oliveira', '156.123.456-33', '31987776688', 'carlos@hotel.com', 'Rua Norte, 45', 'Masculino', '1985-11-11', 'Zelador'),
('Ana Lúcia', '178.654.987-44', '31986668899', 'ana@hotel.com', 'Rua Sul, 12', 'Feminino', '1992-02-14', 'Camareira');


-- Tabela: Hospedes
DROP TABLE IF EXISTS `Hospedes`;
CREATE TABLE `Hospedes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50),
  `cpf` VARCHAR(15),
  `dataNascimento` DATE,
  `telefone` VARCHAR(20),
  `email` VARCHAR(50),
  `endereco` VARCHAR(100),
  `sexo` ENUM('Feminino', 'Masculino', 'Não binário', 'Outro'),
  PRIMARY KEY (`id`)
);

INSERT INTO `Hospedes` (nome, cpf, dataNascimento, telefone, email, endereco, sexo) VALUES
('Caroline Vieira', '123.452.456-75', '2011-11-11', '31989999987', 'carol15@gmail.com', 'Rua Mirai, 816, Lagoa da Prata - MG', 'Feminino'),
('João Marcos', '987.654.321-00', '1999-08-09', '31999996666', 'joao@gmail.com', 'Rua 10, Centro - BH', 'Masculino'),
('Letícia Silva', '456.789.123-77', '1995-03-21', '31988885555', 'leticia@hotmail.com', 'Rua Flores, 101, Contagem', 'Feminino'),
('Pedro Henrique', '321.654.987-88', '1987-01-30', '31987774444', 'pedro@outlook.com', 'Av Principal, 500, Sabará', 'Masculino'),
('Marina Rocha', '147.258.369-99', '2000-12-12', '31984443322', 'marina@gmail.com', 'Rua da Praia, 100, Betim', 'Feminino');

-- Tabela: Quartos
DROP TABLE IF EXISTS `Quartos`;
CREATE TABLE `Quartos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `numero` INT,
  `andar` INT,
  `tipo` VARCHAR(20),
  `valorDiaria` DOUBLE,
  `status` ENUM('Disponível', 'Ocupado', 'Manutenção'),
  PRIMARY KEY (`id`)
);

INSERT INTO `Quartos` (numero, andar, tipo, valorDiaria, status) VALUES
(101, 1, 'Solteiro', 150.00, 'Disponível'),
(102, 1, 'Casal', 200.00, 'Ocupado'),
(201, 2, 'Solteiro', 150.00, 'Disponível'),
(202, 2, 'Casal', 200.00, 'Manutenção'),
(301, 3, 'Luxo', 350.00, 'Disponível');

-- Tabela: Produtos
DROP TABLE IF EXISTS `Produtos`;
CREATE TABLE `Produtos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(100),
  `tamanho` VARCHAR(20),
  `estoque` INT,
  `fornecedor` VARCHAR(50),
  `cnpj` VARCHAR(20),
  PRIMARY KEY (`id`)
);

INSERT INTO `Produtos` (descricao, tamanho, estoque, fornecedor, cnpj) VALUES
('Água Mineral', '500ml', 100, 'Bebidas Minas', '98.765.432/0001-99'),
('Sabonete Lux', '100g', 200, 'Limpeza Top', '11.222.333/0001-44'),
('Papel Higiênico', '30m', 150, 'Papelaria Sol', '77.888.999/0001-55'),
('Suco de Laranja', '1L', 50, 'Bebidas Minas', '98.765.432/0001-99'),
('Shampoo', '200ml', 80, 'Limpeza Top', '11.222.333/0001-44');

-- Tabela: Reservas
DROP TABLE IF EXISTS `Reservas`;
CREATE TABLE `Reservas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idHospede` INT NOT NULL,
  `dataCheckin` DATE,
  `dataCheckout` DATE,
  `diaria` INT,
  `valorTotal` DOUBLE,
  `idQuarto` INT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`idHospede`) REFERENCES `Hospedes`(`id`),
  FOREIGN KEY (`idQuarto`) REFERENCES `Quartos`(`id`)
);

INSERT INTO `Reservas` (idHospede, dataCheckin, dataCheckout, diaria, valorTotal, idQuarto) VALUES
(1, '2025-06-20', '2025-06-22', 2, 300.00, 1),
(2, '2025-06-19', '2025-06-22', 3, 360.00, 2),
(3, '2025-06-18', '2025-06-21', 3, 300.00, 3),
(4, '2025-06-17', '2025-06-20', 3, 390.00, 4),
(5, '2025-06-16', '2025-06-19', 3, 480.00, 5);

-- Tabela: Usuário
DROP TABLE IF EXISTS `Usuarios`;
CREATE TABLE `Usuarios` (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario VARCHAR(50),
  senha VARCHAR(100),
  perfil VARCHAR(30),
  id_funcionario INT,
  FOREIGN KEY (id_funcionario) REFERENCES Funcionarios(id)
);

INSERT INTO `Usuarios` (usuario, senha, perfil, id_funcionario) VALUES
 ('marcelo','123','Administrador', 1),
 ('marcelo','123','Gerente', 1),
 ('marcelo','123','Funcionario', 1),
 ('adm', '123', 'Administrador', 4),
 ('gerente', '1234', 'Gerente', 3),
 ('func', '12345', 'Funcionario', 2),
 ('camareira1', 'abc123', 'Funcionario', 6),
 ('zelador1', 'xyz789', 'Funcionario', 5);

