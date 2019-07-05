/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.dao;

import approptime.entity.Tarefa;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mrg.estagiario
 */
public class TarefaDao extends GenericDao {

   public TarefaDao() {
   }

   public long addTarefa(Tarefa tarefa) throws SQLException {
      tarefa.setId(getNextId("TB_TAREFA"));
      String query = "INSERT INTO approptime.TB_TAREFA (ID, ID_USUARIO, DS_TAREFA) VALUES (?, ?, ?)";
      executeCommand(query, tarefa.getId(), tarefa.getUser().getId(), tarefa.getDescricao());
      closeConnection();
      return tarefa.getId();
   }

   public void removeTarefa(int idTarefa) throws SQLException {
      executeCommand("DELETE FROM approptime.TB_TAREFA WHERE ID = ?", idTarefa);
      closeConnection();
   }

   public void updateTarefa(Tarefa tarefa) throws SQLException {
      String query = "UPDATE approptime.TB_TAREFA SET ID_USUARIO = ?, DS_TAREFA = ? WHERE ID = ?";
      executeCommand(query, tarefa.getUser().getId(), tarefa.getDescricao(), tarefa.getId());
      closeConnection();
   }

   public Tarefa getTarefa(long idTarefa) throws SQLException {
      ResultSet rs = executeQuery("SELECT * FROM approptime.TB_TAREFA WHERE ID = ?", idTarefa);
      Tarefa tarefa = null;
      if (rs.next()) {
         tarefa = populateTarefa(rs);
      }

      rs.close();
      closeConnection();
      return tarefa;
   }

   public List<Tarefa> getTarefaByIdUser(long idUser) throws SQLException {
      ResultSet rs = executeQuery("SELECT * FROM approptime.TB_TAREFA WHERE ID_USUARIO = ?", idUser);
      List<Tarefa> toReturn = new LinkedList<>();
      while (rs.next()) {
         toReturn.add(populateTarefa(rs));
      }
      rs.close();
      closeConnection();
      return toReturn;
   }


   /***
    * Busca as tarefas que ocorreram nos últimos 60 dias
    * @param idUser
    * @return
    * @throws SQLException
    */
   public List<Tarefa> getTarefasMaisRecentes(Long idUser) throws SQLException {
      StringBuilder sql = new StringBuilder();

      sql.append("SELECT T.ID,                                                       ");
      sql.append("       T.ID_USUARIO,                                               ");
      sql.append("       T.DS_TAREFA,                                                ");
      sql.append("       COUNT(*) AS QTD_ATIVIDADES,                                 ");
      sql.append("       MAX(DT_TERMINO) AS ULTIMA_ATIVIDADE                         ");
      sql.append("  FROM approptime.TB_TAREFA T                                      ");
      sql.append("    LEFT JOIN approptime.TB_ATIVIDADE A ON (A.ID_TAREFA = T.ID)    ");
      sql.append("  WHERE T.ID_USUARIO = ?                                           ");
      sql.append("    AND DT_TERMINO > CURRENT_DATE()-60                             ");
      sql.append("  GROUP BY T.ID                                                    ");
      sql.append("  ORDER BY MAX(A.DT_TERMINO) DESC,                                 ");
      sql.append("           COUNT(*) DESC;                                          ");

      ResultSet rs = executeQuery(sql.toString(), idUser);

      List<Tarefa> toReturn = new LinkedList<>();
      while (rs.next()) {
         toReturn.add(populateTarefa(rs));
      }
      rs.close();
      closeConnection();
      return toReturn;
   }

   public Tarefa getTarefaByDescricao(String descricao, long idUsuario) throws SQLException {
      StringBuilder sql = new StringBuilder();

      sql.append("SELECT T.ID,                             ");
      sql.append("       T.ID_USUARIO,                     ");
      sql.append("       T.DS_TAREFA                       ");
      sql.append("  FROM approptime.TB_TAREFA T            ");
      sql.append("  WHERE T.ID_USUARIO = ?                 ");
      sql.append("    AND LOWER(T.DS_TAREFA) like LOWER(?) ");

      ResultSet rs = executeQuery(sql.toString(),  idUsuario, descricao);

      Tarefa toReturn = null;
      if (rs.next()) {
         toReturn = populateTarefa(rs);
      }
      rs.close();
      closeConnection();
      return toReturn;
   }

   public List<String> getDescricaoTarefasByRelevancia(long idUsuario) throws SQLException {
      List<String> descricoesTarefas = new ArrayList<>();
      List<Tarefa> tarefaByRelevancia = getTarefaByIdUser(idUsuario);
      for (Tarefa tarefa : tarefaByRelevancia) {
         descricoesTarefas.add(tarefa.getDescricao());
      }
      closeConnection();
      return descricoesTarefas;
   }

   public static Tarefa populateTarefa(ResultSet rs) throws SQLException {
      final UsuarioDao userDao = new UsuarioDao();
      Tarefa toReturn = new Tarefa();
      toReturn.setId(rs.getLong("ID"));
      toReturn.setUser(userDao.getUser(rs.getLong("ID_USUARIO")));
      toReturn.setDescricao(rs.getString("DS_TAREFA"));
      return toReturn;
   }
}
