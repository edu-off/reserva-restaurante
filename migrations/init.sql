-- -----------------------------------------------------
-- Table `reserva-database`.`cliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `reserva-database`.`cliente` ;

CREATE TABLE IF NOT EXISTS `reserva-database`.`cliente` (
    `email` VARCHAR(100) NOT NULL,
    `nome` VARCHAR(50) NOT NULL,
    `ddd` INT NULL,
    `telefone` INT NULL,
    PRIMARY KEY (`email`));


-- -----------------------------------------------------
-- Table `reserva-database`.`restaurante`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `reserva-database`.`restaurante` ;

CREATE TABLE IF NOT EXISTS `reserva-database`.`restaurante` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(50) NOT NULL,
    `culinaria` VARCHAR(100) NOT NULL,
    `capacidade` INT NOT NULL,
    `horario_abertura` TIME NOT NULL,
    `horario_encerramento` TIME NOT NULL,
    `ddd` INT NOT NULL,
    `telefone` INT NOT NULL,
    PRIMARY KEY (`id`));

CREATE INDEX `restaurante_nome_idx` ON `reserva-database`.`restaurante` (`nome` ASC) INVISIBLE;

CREATE INDEX `restaurante_culinaria_idx` ON `reserva-database`.`restaurante` (`culinaria` ASC) VISIBLE;

-- -----------------------------------------------------
-- Table `reserva-database`.`mesa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `reserva-database`.`mesa` ;

CREATE TABLE IF NOT EXISTS `reserva-database`.`mesa` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `status` ENUM('DISPONIVEL', 'RESERVADA') NOT NULL,
    `capacidade` INT NOT NULL,
    `restaurante_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_mesa_restaurante`
    FOREIGN KEY (`restaurante_id`)
    REFERENCES `reserva-database`.`restaurante` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE INDEX `fk_mesa_restaurante_idx` ON `reserva-database`.`mesa` (`restaurante_id` ASC) VISIBLE;

-- -----------------------------------------------------
-- Table `reserva-database`.`reserva`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `reserva-database`.`reserva` ;

CREATE TABLE IF NOT EXISTS `reserva-database`.`reserva` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `status` ENUM('ATIVA', 'CANCELADA', 'FINALIZADA') NOT NULL,
    `agendamento` DATETIME NOT NULL,
    `quantidade_pessoas` INT NOT NULL,
    `cliente_email` VARCHAR(100) NOT NULL,
    `restaurante_id` BIGINT NOT NULL,
    `mesa_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_reserva_cliente`
    FOREIGN KEY (`cliente_email`)
    REFERENCES `reserva-database`.`cliente` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_reserva_restaurante`
    FOREIGN KEY (`restaurante_id`)
    REFERENCES `reserva-database`.`restaurante` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_reserva_mesa`
    FOREIGN KEY (`mesa_id`)
    REFERENCES `reserva-database`.`mesa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE INDEX `fk_reserva_cliente_idx` ON `reserva-database`.`reserva` (`cliente_email` ASC) VISIBLE;

CREATE INDEX `fk_reserva_restaurante_idx` ON `reserva-database`.`reserva` (`restaurante_id` ASC) VISIBLE;

CREATE INDEX `fk_reserva_mesa_idx` ON `reserva-database`.`reserva` (`mesa_id` ASC) VISIBLE;

CREATE INDEX `reserva_agendamento_idx` ON `reserva-database`.`reserva` (`agendamento` ASC) VISIBLE;

-- -----------------------------------------------------
-- Table `reserva-database`.`avaliacao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `reserva-database`.`avaliacao` ;

CREATE TABLE IF NOT EXISTS `reserva-database`.`avaliacao` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `titulo` VARCHAR(40) NOT NULL,
    `comentario` VARCHAR(200) NOT NULL,
    `nota` INT NOT NULL,
    `reserva_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_avaliacao_reserva`
    FOREIGN KEY (`reserva_id`)
    REFERENCES `reserva-database`.`reserva` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE INDEX `fk_avaliacao_reserva_idx` ON `reserva-database`.`avaliacao` (`reserva_id` ASC) VISIBLE;

-- -----------------------------------------------------
-- Table `reserva-database`.`endereco`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `reserva-database`.`endereco` ;

CREATE TABLE IF NOT EXISTS `reserva-database`.`endereco` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `logradouro` VARCHAR(50) NOT NULL,
    `numero` VARCHAR(10) NOT NULL,
    `bairro` VARCHAR(20) NOT NULL,
    `cidade` VARCHAR(20) NOT NULL,
    `uf` VARCHAR(2) NOT NULL,
    `cep` INT NOT NULL,
    `restaurante_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_endereco_restaurante`
    FOREIGN KEY (`restaurante_id`)
    REFERENCES `reserva-database`.`restaurante` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE INDEX `fk_endereco_restaurante_idx` ON `reserva-database`.`endereco` (`restaurante_id` ASC) VISIBLE;
