/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.dao;

import approptime.util.Constantes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 *
 * @author mrg.estagiario
 */
public class GenericDao {

   private static String user;
   private static String password;
   private static String host;
   private static String port;
   private List<GenericDao> daosSharedConnection = new ArrayList<>();
   private Boolean transacaoAtiva = false;
   private Connection connection;

   public GenericDao() {
   }

   public void setUser(String user) {
      this.user = user;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setHost(String host) {
      this.host = host;
   }

   public void setPort(String port) {
      this.port = port;
   }

   public String[] dadosDeConexao(){
      String[] toReturn = new String[3];
      toReturn[0] = "MySQL";
      toReturn[1] = GenericDao.host;
      toReturn[2] = GenericDao.user;
      return toReturn;
   }

   /**
    * *
    * Faz a conexão com o Banco de dados sem usar o nome da base, para poder
    * criar uma nova base
    *
    * @return
    * @throws SQLException
    */
   protected Connection getConnectionToCreateDatabase() throws SQLException {
      if (Objects.isNull(connection)) {
         Properties properties = new Properties();
         properties.setProperty("user", this.user);
         properties.setProperty("password", this.password);
         properties.setProperty("useSSL", "false");
         properties.setProperty("useTimezone", "true");
         properties.setProperty("serverTimezone", "UTC");
         String dbUrl = host + ":" + port + "/";
         connection = DriverManager.getConnection("jdbc:mysql://" + dbUrl, properties);
      }
      return connection;
   }

   protected Connection getConnection() throws SQLException {
      if (Objects.isNull(connection)) {
         Properties properties = new Properties();
         properties.setProperty("user", this.user);
         properties.setProperty("password", this.password);
         properties.setProperty("useSSL", "false");
         properties.setProperty("autoReconnect", "true");
         properties.setProperty("maxReconnects", "10");
         properties.setProperty("useTimezone", "true");
         properties.setProperty("serverTimezone", "UTC");

         String dbUrl = host + ":" + port + "/" + Constantes.BASE_NAME;
         connection = DriverManager.getConnection("jdbc:mysql://" + dbUrl, properties);
      }
      return connection;
   }

   public Statement getStatement() throws SQLException {
      return getConnection().createStatement();
   }

   public PreparedStatement getPreparedStatement(String st) throws SQLException {
      return getConnection().prepareStatement(st);
   }

   /**
    * *
    * Pega o preparedStatement sem usar o nome da base na url do DriveManager
    *
    * @param st
    * @return
    * @throws SQLException
    */
   public PreparedStatement getPreparedStatementToCreateDatabase(String st) throws SQLException {
      return getConnectionToCreateDatabase().prepareStatement(st);
   }

   public ResultSet executeQuery(String query, Object... params) throws SQLException {
      PreparedStatement ps = getPreparedStatement(query);
      for (int i = 0; i < params.length; i++) {
         ps.setObject(i + 1, params[i]);
      }
      ResultSet rs = ps.executeQuery();
      return rs;
   }

   /**
    *
    * @param query
    * @param params
    * @return
    * @throws SQLException
    */
   public int executeCommand(String query, Object... params) throws SQLException {
      PreparedStatement ps = getPreparedStatement(query);
      for (int i = 0; i < params.length; i++) {
         ps.setObject(i + 1, params[i]);
      }
      int result = ps.executeUpdate();
      ps.close();
      return result;
   }

   /**
    * *
    * Início de transação
    *
    * @param daos Lista de todas as daos que estão envolvidas na operação a ser
    * transacional
    * @throws SQLException
    */
   public void beginTransaction(GenericDao... daos) throws SQLException {
      if (transacaoAtiva) {
         throw new RuntimeException("Já existe uma transação iniciada para a dao " + this.getClass().getName());
      }
      getConnection().setAutoCommit(false);
      daosSharedConnection.clear();
      for (GenericDao dao : daos) {
         //Criando uma conexão e compartilhando com todos os Dao's para viabilizar transação única
         dao.setConnection(connection);
         daosSharedConnection.add(dao);
      }
      transacaoAtiva = true;
   }

   /**
    * *
    * Commita a transação e libera a conexão compartilhada entre as daos
    * envolvidas na transação
    *
    * @throws SQLException
    */
   public void commitTransaction() throws SQLException {
      getConnection().commit();
      getConnection().setAutoCommit(true);
      closeConnection();

      for (GenericDao dao : daosSharedConnection) {
         //Limpando conexão das daos que compartilharam conexão nesta transação
         dao.setConnection(null);
      }
      daosSharedConnection.clear();
      transacaoAtiva = false;
   }

   /**
    * *
    * Cancela a transação e libera a conexão compartilhada entre as daos
    * envolvidas na transação
    *
    * @throws SQLException
    */
   public void rollbackTransaction() throws SQLException {
      getConnection().rollback();
      getConnection().setAutoCommit(true);
      closeConnection();

      for (GenericDao dao : daosSharedConnection) {
         //Limpando conexão das daos que compartilharam conexão nesta transação
         dao.setConnection(null);
      }
      daosSharedConnection.clear();
      transacaoAtiva = false;
   }

   public void closeConnection() throws SQLException {
      if (Objects.isNull(connection)) {
         return;
      }

      //Se estou em uma operação transacionada (begin, commit, rollback) então não posso fechar a conexão
      if (connection.getAutoCommit() == false) {
         return;
      }

      getConnection().close();
      connection = null;
   }

   public void setConnection(Connection connection) {
      this.connection = connection;
   }

   public long getNextId(String tableName) throws SQLException {
      ResultSet rs = executeQuery("SELECT MAX(ID) FROM approptime." + tableName);
      rs.next();
      Object result = rs.getObject(1);
      if (result == null) {
         rs.close();
         return 1;
      } else {
         return (long) result + 1;
      }
   }

   /**
    * *
    * Verifica se a database com o nome da constante BASE_NAME existe
    *
    * @return true se a base existe e false caso contrário
    * @throws SQLException
    */
   public boolean verificaDatabase() throws SQLException {
      PreparedStatement ps = getPreparedStatement("show databases like ?");
      ps.setObject(1, Constantes.BASE_NAME);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
         return true;
      } else {
         return false;
      }
   }
}
