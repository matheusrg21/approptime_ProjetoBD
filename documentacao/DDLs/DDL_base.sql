CREATE SCHEMA IF NOT EXISTS `approptime` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `approptime`.`TB_ATIVIDADE` (
  `ID` BIGINT(10) NOT NULL,
  `ID_TAREFA` BIGINT(10) NOT NULL,
  `DT_INICIO` DATETIME NOT NULL,
  `DT_TERMINO` DATETIME NULL,
  `DS_OBSERVACOES` VARCHAR(2000) NULL,
  PRIMARY KEY (`ID`));

CREATE TABLE `approptime`.`TB_TAREFA` (
  `ID` BIGINT(10) NOT NULL,
  `ID_USUARIO` BIGINT(10) NOT NULL,
  `DS_TAREFA` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`ID`));

CREATE TABLE `approptime`.`TB_USUARIO` (
  `ID` BIGINT(10) NOT NULL,
  `DS_LOGIN` VARCHAR(80) NOT NULL,
  `DS_NOME` VARCHAR(200) NOT NULL,
  `DS_PASSWORD` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `DS_LOGIN_UNIQUE` (`DS_LOGIN` ASC));

CREATE TABLE `approptime`.`tb_relatorio` (
  `ID` INT NOT NULL,
  `ID_USUARIO` BIGINT(10) NOT NULL,
  `DS_NOME` VARCHAR(45) NOT NULL,
  `DS_DESCRICAO` VARCHAR(500) NULL,
  `DS_QUERY` VARCHAR(5000) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_USUARIO_DS_NOME_UNIQUE` (`ID_USUARIO` ASC, `DS_NOME` ASC));


ALTER TABLE `approptime`.`TB_ATIVIDADE` ADD INDEX `fk_TB_ATIVIDADE_TB_TAREFA_idx` (`ID_TAREFA` ASC);
ALTER TABLE `approptime`.`TB_ATIVIDADE` ADD CONSTRAINT `fk_TB_ATIVIDADE_TB_TAREFA`
  FOREIGN KEY (`ID_TAREFA`)
  REFERENCES `approptime`.`TB_TAREFA` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `approptime`.`TB_TAREFA` ADD INDEX `fk_TB_TAREFA_TB_USUARIO_idx` (`ID_USUARIO` ASC);
ALTER TABLE `approptime`.`TB_TAREFA` ADD UNIQUE INDEX `DS_TAREFA_ID_USUARIO_UNIQUE` (`DS_TAREFA` ASC, `ID_USUARIO` ASC);
ALTER TABLE `approptime`.`TB_TAREFA` ADD CONSTRAINT `fk_TB_TAREFA_TB_USUARIO`
  FOREIGN KEY (`ID_USUARIO`)
  REFERENCES `approptime`.`TB_USUARIO` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `approptime`.`tb_relatorio` ADD CONSTRAINT `fk_TB_RELATORIO_TB_USUARIO`
    FOREIGN KEY (`ID_USUARIO`)
    REFERENCES `approptime`.`tb_usuario` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION;


