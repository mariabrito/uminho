-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema habitat
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema habitat
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `habitat` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `habitat` ;

-- -----------------------------------------------------
-- Table `habitat`.`Atividade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Atividade` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Doador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Doador` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(100) NOT NULL,
  `Tipo` TINYINT(1) NOT NULL,
  `Morada` VARCHAR(100) NOT NULL,
  `NIB` VARCHAR(30) NOT NULL,
  `NIF` VARCHAR(10) NOT NULL,
  `Observacoes` VARCHAR(500) NULL,
  `AtividadeID` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Doador_Atividade1_idx` (`AtividadeID` ASC),
  CONSTRAINT `fk_Doador_Atividade1`
    FOREIGN KEY (`AtividadeID`)
    REFERENCES `habitat`.`Atividade` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Funcionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Funcionario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(100) NOT NULL,
  `DataNascimento` DATE NOT NULL,
  `Morada` VARCHAR(100) NOT NULL,
  `NIF` VARCHAR(10) NOT NULL,
  `NIB` VARCHAR(30) NULL,
  `Escolaridade` VARCHAR(100) NULL,
  `Nacionalidade` VARCHAR(100) NULL,
  `Naturalidade` VARCHAR(100) NULL,
  `EstadoCivil` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Equipa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Equipa` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(100) NOT NULL,
  `Voluntario` INT NULL,
  `FuncionarioResp` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Equipa_Voluntario1_idx` (`Voluntario` ASC),
  INDEX `fk_Equipa_Funcionario1_idx` (`FuncionarioResp` ASC),
  CONSTRAINT `fk_Equipa_Voluntario1`
    FOREIGN KEY (`Voluntario`)
    REFERENCES `habitat`.`Voluntario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Equipa_Funcionario1`
    FOREIGN KEY (`FuncionarioResp`)
    REFERENCES `habitat`.`Funcionario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Voluntario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Voluntario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(100) NOT NULL,
  `Morada` VARCHAR(100) NOT NULL,
  `NIF` VARCHAR(10) NOT NULL,
  `NIB` VARCHAR(30) NOT NULL,
  `DataNascimento` DATE NOT NULL,
  `Nacionalidade` VARCHAR(100) NULL,
  `Naturalidade` VARCHAR(100) NULL,
  `EstadoCivil` VARCHAR(45) NULL,
  `Observacoes` VARCHAR(500) NULL,
  `Escolaridade` VARCHAR(45) NULL,
  `Ficheiro` BLOB NULL,
  `DoadorID` INT NULL,
  `AtividadeID` INT NOT NULL,
  `EquipaID` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Voluntario_Atividade1_idx` (`AtividadeID` ASC),
  INDEX `fk_Voluntario_Equipa1_idx` (`EquipaID` ASC),
  INDEX `fk_Voluntario_Doador1_idx` (`DoadorID` ASC),
  CONSTRAINT `fk_Voluntario_Atividade1`
    FOREIGN KEY (`AtividadeID`)
    REFERENCES `habitat`.`Atividade` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Voluntario_Equipa1`
    FOREIGN KEY (`EquipaID`)
    REFERENCES `habitat`.`Equipa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Voluntario_Doador1`
    FOREIGN KEY (`DoadorID`)
    REFERENCES `habitat`.`Doador` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Contacto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Contacto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` INT NOT NULL,
  `Valor` VARCHAR(30) NOT NULL,
  `DonoID` INT NOT NULL,
  `TipoDono` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Contacto_Doador1_idx` (`DonoID` ASC),
  CONSTRAINT `fk_Contacto_Doador1`
    FOREIGN KEY (`DonoID`)
    REFERENCES `habitat`.`Doador` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Contacto_Voluntario1`
    FOREIGN KEY (`DonoID`)
    REFERENCES `habitat`.`Voluntario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Representante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Representante` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(100) NOT NULL,
  `DataNascimento` DATE NOT NULL,
  `NIF` VARCHAR(10) NOT NULL,
  `NIB` VARCHAR(30) NOT NULL,
  `Nacionalidade` VARCHAR(45) NULL,
  `Naturalidade` VARCHAR(45) NULL,
  `Escolaridade` VARCHAR(45) NULL,
  `EstadoCivil` VARCHAR(45) NULL,
  `AtividadeID` INT NOT NULL,
  `ContactoID` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Representante_Atividade1_idx` (`AtividadeID` ASC),
  INDEX `fk_Representante_Contacto1_idx` (`ContactoID` ASC),
  CONSTRAINT `fk_Representante_Atividade1`
    FOREIGN KEY (`AtividadeID`)
    REFERENCES `habitat`.`Atividade` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Representante_Contacto1`
    FOREIGN KEY (`ContactoID`)
    REFERENCES `habitat`.`Contacto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Familia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Familia` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(100) NOT NULL,
  `Morada` VARCHAR(100) NOT NULL,
  `Aprovada` TINYINT(1) NOT NULL,
  `Rendimento` DECIMAL(10,2) NULL,
  `Observacoes` VARCHAR(700) NULL,
  `RepresentanteID` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Familia_Representante1_idx` (`RepresentanteID` ASC),
  CONSTRAINT `fk_Familia_Representante1`
    FOREIGN KEY (`RepresentanteID`)
    REFERENCES `habitat`.`Representante` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Membro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Membro` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `DataNascimento` DATE NOT NULL,
  `GrauParentesco` VARCHAR(45) NOT NULL,
  `FamiliaID` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Membro_Familia1_idx` (`FamiliaID` ASC),
  CONSTRAINT `fk_Membro_Familia1`
    FOREIGN KEY (`FamiliaID`)
    REFERENCES `habitat`.`Familia` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Candidatura`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Candidatura` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `DataAplicacao` DATE NOT NULL,
  `TerrenoConstrucao` VARCHAR(100) NOT NULL,
  `Prioridade` INT NOT NULL,
  `Estado` TINYINT(1) NOT NULL,
  `DataAprovacao` DATE NULL,
  `Ficheiro` BLOB NULL,
  `Observacoes` VARCHAR(500) NULL,
  `FuncionarioResp` INT NOT NULL,
  `FamiliaID` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Candidatura_Familia1_idx` (`FamiliaID` ASC),
  INDEX `fk_Candidatura_Funcionario1_idx` (`FuncionarioResp` ASC),
  CONSTRAINT `fk_Candidatura_Familia1`
    FOREIGN KEY (`FamiliaID`)
    REFERENCES `habitat`.`Familia` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Candidatura_Funcionario1`
    FOREIGN KEY (`FuncionarioResp`)
    REFERENCES `habitat`.`Funcionario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Pergunta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Pergunta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `PergTexto` VARCHAR(200) NOT NULL,
  `Ativa` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`CandidaturaPergunta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`CandidaturaPergunta` (
  `CandidaturaID` INT NOT NULL,
  `PerguntaID` INT NOT NULL,
  `RespTexto` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`CandidaturaID`, `PerguntaID`),
  INDEX `fk_Resposta_Candidatura_idx` (`CandidaturaID` ASC),
  INDEX `fk_Resposta_Perguta1_idx` (`PerguntaID` ASC),
  CONSTRAINT `fk_Resposta_Candidatura`
    FOREIGN KEY (`CandidaturaID`)
    REFERENCES `habitat`.`Candidatura` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Resposta_Perguta1`
    FOREIGN KEY (`PerguntaID`)
    REFERENCES `habitat`.`Pergunta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`PlanoPagamentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`PlanoPagamentos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ProximaPrestacao` DECIMAL(10,2) NOT NULL,
  `Observacoes` VARCHAR(500) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Projeto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Projeto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(100) NOT NULL,
  `DataInicio` DATE NOT NULL,
  `Orcamento` DECIMAL(10,2) NULL,
  `DataFinalPrevista` DATE NULL,
  `DataFinal` DATE NULL,
  `DataAssinContr` DATE NULL,
  `DataEntrgChave` DATE NULL,
  `CustoFinal` DECIMAL(10,2) NULL,
  `Observacoes` VARCHAR(700) NULL,
  `CandidaturaID` INT NOT NULL,
  `PlanoPagamentosID` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Projeto_Candidatura1_idx` (`CandidaturaID` ASC),
  INDEX `fk_Projeto_PlanoPagamentos1_idx` (`PlanoPagamentosID` ASC),
  CONSTRAINT `fk_Projeto_Candidatura1`
    FOREIGN KEY (`CandidaturaID`)
    REFERENCES `habitat`.`Candidatura` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Projeto_PlanoPagamentos1`
    FOREIGN KEY (`PlanoPagamentosID`)
    REFERENCES `habitat`.`PlanoPagamentos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Prestacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Prestacao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Data` DATE NOT NULL,
  `Valor` INT NOT NULL,
  `Estado` INT NOT NULL,
  `PlanoPagamentosID` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Prestacao_PlanoPagamentos1_idx` (`PlanoPagamentosID` ASC),
  CONSTRAINT `fk_Prestacao_PlanoPagamentos1`
    FOREIGN KEY (`PlanoPagamentosID`)
    REFERENCES `habitat`.`PlanoPagamentos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`NomeMaterial`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`NomeMaterial` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Material`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Material` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Quantidade` INT NOT NULL,
  `NomeMaterialID` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Material_NomeMaterial1_idx` (`NomeMaterialID` ASC),
  CONSTRAINT `fk_Material_NomeMaterial1`
    FOREIGN KEY (`NomeMaterialID`)
    REFERENCES `habitat`.`NomeMaterial` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Donativo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Donativo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Data` DATE NOT NULL,
  `Tipo` INT NOT NULL,
  `Valor` DECIMAL(10,2) NOT NULL,
  `Quantia` INT NOT NULL,
  `MaterialID` INT NOT NULL,
  `DoadorID` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Donativo_Material1_idx` (`MaterialID` ASC),
  INDEX `fk_Donativo_Doador1_idx` (`DoadorID` ASC),
  CONSTRAINT `fk_Donativo_Material1`
    FOREIGN KEY (`MaterialID`)
    REFERENCES `habitat`.`Material` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Donativo_Doador1`
    FOREIGN KEY (`DoadorID`)
    REFERENCES `habitat`.`Doador` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Evento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Evento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Data` DATE NOT NULL,
  `Local` VARCHAR(100) NOT NULL,
  `ValorObtido` DECIMAL(10,2) NOT NULL,
  `NrParticipantes` INT NULL,
  `Observacoes` VARCHAR(500) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`Tarefa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`Tarefa` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(50) NOT NULL,
  `DataInicio` DECIMAL(10,2) NOT NULL,
  `DataFinal` DECIMAL(10,2) NOT NULL,
  `Estado` INT NOT NULL,
  `ProjetoID` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Tarefa_Projeto1_idx` (`ProjetoID` ASC),
  CONSTRAINT `fk_Tarefa_Projeto1`
    FOREIGN KEY (`ProjetoID`)
    REFERENCES `habitat`.`Projeto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`VoluntarioTarefa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`VoluntarioTarefa` (
  `VoluntarioID` INT NOT NULL,
  `TarefaID` INT NOT NULL,
  `NrHoras` INT NULL,
  PRIMARY KEY (`VoluntarioID`, `TarefaID`),
  INDEX `fk_Voluntario_has_Tarefa_Tarefa1_idx` (`TarefaID` ASC),
  INDEX `fk_Voluntario_has_Tarefa_Voluntario1_idx` (`VoluntarioID` ASC),
  CONSTRAINT `fk_Voluntario_has_Tarefa_Voluntario1`
    FOREIGN KEY (`VoluntarioID`)
    REFERENCES `habitat`.`Voluntario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Voluntario_has_Tarefa_Tarefa1`
    FOREIGN KEY (`TarefaID`)
    REFERENCES `habitat`.`Tarefa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`DonativoProjeto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`DonativoProjeto` (
  `DonativoID` INT NOT NULL,
  `ProjetoID` INT NOT NULL,
  PRIMARY KEY (`DonativoID`, `ProjetoID`),
  INDEX `fk_Donativo_has_Projeto_Projeto1_idx` (`ProjetoID` ASC),
  INDEX `fk_Donativo_has_Projeto_Donativo1_idx` (`DonativoID` ASC),
  CONSTRAINT `fk_Donativo_has_Projeto_Donativo1`
    FOREIGN KEY (`DonativoID`)
    REFERENCES `habitat`.`Donativo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Donativo_has_Projeto_Projeto1`
    FOREIGN KEY (`ProjetoID`)
    REFERENCES `habitat`.`Projeto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `habitat`.`EventoVoluntario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `habitat`.`EventoVoluntario` (
  `EventoID` INT NULL,
  `VoluntarioID` INT NOT NULL,
  PRIMARY KEY (`EventoID`, `VoluntarioID`),
  INDEX `fk_Evento_has_Voluntario_Voluntario1_idx` (`VoluntarioID` ASC),
  INDEX `fk_Evento_has_Voluntario_Evento1_idx` (`EventoID` ASC),
  CONSTRAINT `fk_Evento_has_Voluntario_Evento1`
    FOREIGN KEY (`EventoID`)
    REFERENCES `habitat`.`Evento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Evento_has_Voluntario_Voluntario1`
    FOREIGN KEY (`VoluntarioID`)
    REFERENCES `habitat`.`Voluntario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
