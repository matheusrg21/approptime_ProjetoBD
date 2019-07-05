/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.dao;

import approptime.frame.TelaPrincipal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author mrg.estagiario
 */
public class EstruturaDao extends GenericDao {

   public List<String> getDDLBaseSistema() {
      List<String> ddl = new ArrayList<>();

      ddl.add(" CREATE SCHEMA `approptime` DEFAULT CHARACTER SET utf8 ;");

      ddl.add(" CREATE TABLE `approptime`.`TB_ATIVIDADE` (");
      ddl.add("   `ID` BIGINT(10) NOT NULL,");
      ddl.add("   `ID_TAREFA` BIGINT(10) NOT NULL,");
      ddl.add("   `DT_INICIO` DATETIME NOT NULL,");
      ddl.add("   `DT_TERMINO` DATETIME NULL,");
      ddl.add("   `DS_OBSERVACOES` VARCHAR(2000) NULL,");
      ddl.add("   PRIMARY KEY (`ID`));");

      ddl.add(" CREATE TABLE `approptime`.`TB_TAREFA` (");
      ddl.add("   `ID` BIGINT(10) NOT NULL,");
      ddl.add("   `ID_USUARIO` BIGINT(10) NOT NULL,");
      ddl.add("   `DS_TAREFA` VARCHAR(250) NOT NULL,");
      ddl.add("   PRIMARY KEY (`ID`));");

      ddl.add(" CREATE TABLE `approptime`.`TB_USUARIO` (");
      ddl.add("   `ID` BIGINT(10) NOT NULL,");
      ddl.add("   `DS_LOGIN` VARCHAR(80) NOT NULL,");
      ddl.add("   `DS_NOME` VARCHAR(200) NOT NULL,");
      ddl.add("   `DS_PASSWORD` VARCHAR(35) NOT NULL,");
      ddl.add("   PRIMARY KEY (`ID`),");
      ddl.add("   UNIQUE INDEX `DS_LOGIN_UNIQUE` (`DS_LOGIN` ASC));");

      ddl.add("  CREATE TABLE `approptime`.`tb_relatorio` (");
      ddl.add("    `ID` INT NOT NULL,");
      ddl.add("    `ID_USUARIO` BIGINT(10) NOT NULL,");
      ddl.add("    `DS_NOME` VARCHAR(45) NOT NULL,");
      ddl.add("    `DS_DESCRICAO` VARCHAR(500) NULL,");
      ddl.add("    `DS_QUERY` VARCHAR(5000) NOT NULL,");
      ddl.add("    PRIMARY KEY (`ID`),");
      ddl.add("    UNIQUE INDEX `ID_USUARIO_DS_NOME_UNIQUE` (`ID_USUARIO` ASC, `DS_NOME` ASC));");

      ddl.add(" ALTER TABLE `approptime`.`TB_ATIVIDADE` ");
      ddl.add(" ADD INDEX `fk_TB_ATIVIDADE_TB_TAREFA_idx` (`ID_TAREFA` ASC);");
      ddl.add(" ALTER TABLE `approptime`.`TB_ATIVIDADE` ");
      ddl.add(" ADD CONSTRAINT `fk_TB_ATIVIDADE_TB_TAREFA`");
      ddl.add("   FOREIGN KEY (`ID_TAREFA`)");
      ddl.add("   REFERENCES `approptime`.`TB_TAREFA` (`ID`)");
      ddl.add("   ON DELETE NO ACTION");
      ddl.add("   ON UPDATE NO ACTION;");

      ddl.add(" ALTER TABLE `approptime`.`TB_TAREFA` ");
      ddl.add(" ADD INDEX `fk_TB_TAREFA_TB_USUARIO_idx` (`ID_USUARIO` ASC);");
      ddl.add(" ALTER TABLE `approptime`.`TB_TAREFA` ");
      ddl.add(" ADD CONSTRAINT `fk_TB_TAREFA_TB_USUARIO`");
      ddl.add("   FOREIGN KEY (`ID_USUARIO`)");
      ddl.add("   REFERENCES `approptime`.`TB_USUARIO` (`ID`)");
      ddl.add("   ON DELETE NO ACTION");
      ddl.add("   ON UPDATE NO ACTION;");

      ddl.add("  ALTER TABLE `approptime`.`tb_relatorio` ADD CONSTRAINT `fk_TB_RELATORIO_TB_USUARIO` ");
      ddl.add("      FOREIGN KEY (`ID_USUARIO`)");
      ddl.add("      REFERENCES `approptime`.`tb_usuario` (`ID`)");
      ddl.add("      ON DELETE NO ACTION");
      ddl.add("      ON UPDATE NO ACTION;");

      ddl.add(" ALTER TABLE `approptime`.`TB_TAREFA` ");
      ddl.add(" ADD UNIQUE INDEX `DS_TAREFA_ID_USUARIO_UNIQUE` (`DS_TAREFA` ASC, `ID_USUARIO` ASC);");

      return ddl;
   }

   public void createDataBase() {
      try {
         String[] cmd = getDDLBaseSistema().stream()
                 .collect(Collectors.joining(""))
                 .split(";");
         for (String c : cmd) {
            if (!c.isEmpty()) {
               getPreparedStatementToCreateDatabase(c).executeUpdate(c);
            }
         }
      } catch (SQLException ex) {
         if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(EstruturaDao.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
   }
}
