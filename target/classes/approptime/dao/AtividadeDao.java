/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.dao;

import approptime.dao.filter.AtividadeFilter;
import approptime.dto.AgregadoDto;
import approptime.entity.Atividade;
import approptime.entity.Tarefa;
import approptime.util.Constantes;
import approptime.util.UtilMethods;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author mrg.estagiario
 */
public class AtividadeDao extends GenericDao {

   public AtividadeDao() {
   }

   public long addAtividadeComTag(Atividade atividade) throws SQLException {
      atividade.setId(getNextId("TB_ATIVIDADE"));
      String query = "INSERT INTO approptime.TB_ATIVIDADE (ID, ID_TAREFA, DT_INICIO, DT_TERMINO, DS_OBSERVACOES, ID_TAG) VALUES (?, ?, ?, ?, ?, ?)";
      executeCommand(query, atividade.getId(), atividade.getTarefa().getId(), atividade.getInicio(), atividade.getTermino(), atividade.getObservacoes(), atividade.getTag().getId());
      closeConnection();
      return atividade.getId();
   }

   public void removeAtividade(Long idAtividade) throws SQLException {
      executeCommand("DELETE FROM approptime.TB_ATIVIDADE WHERE ID = ?", idAtividade);
      closeConnection();
   }

   public void updateAtividade(Atividade atividade) throws SQLException {
      StringBuilder sql = new StringBuilder();
      sql.append("UPDATE                                                       ");
      sql.append("  approptime.TB_ATIVIDADE A                                  ");
      sql.append("   INNER JOIN approptime.TB_TAREFA T ON (A.ID_TAREFA = T.ID) ");
      sql.append("    SET ID_TAREFA = ?,                                       ");
      sql.append("        DT_INICIO = ?,                                       ");
      sql.append("        DT_TERMINO = ?,                                      ");
      sql.append("        DS_OBSERVACOES = ?                                   ");
      if(Objects.nonNull(atividade.getTag())){
        sql.append("       ,ID_TAG = ?                                         ");  
        
      sql.append("    WHERE A.ID = ?                                           ");
      sql.append("      AND T.ID_USUARIO = ?                                   ");

      executeCommand(sql.toString(), atividade.getTarefa().getId(), atividade.getInicio(), atividade.getTermino(), atividade.getObservacoes(),
                     atividade.getTag().getId(), atividade.getId(), atividade.getTarefa().getUser().getId());
      }
      else{
          
        sql.append("    WHERE A.ID = ?                                           ");
        sql.append("      AND T.ID_USUARIO = ?                                   ");

        executeCommand(sql.toString(), atividade.getTarefa().getId(), atividade.getInicio(), atividade.getTermino(), atividade.getObservacoes(),
                       atividade.getId(), atividade.getTarefa().getUser().getId());
      }

      closeConnection();
   }

   public Atividade getAtividadeById(long idAtividade) throws SQLException {
      ResultSet rs = executeQuery("SELECT * FROM approptime.TB_ATIVIDADE WHERE ID = ? ORDER BY DT_TERMINO DESC", idAtividade);
      Atividade atividade = null;
      if (rs.next()) {
         atividade = populateAtividade(rs);
      }

      rs.close();
      closeConnection();
      return atividade;
   }

   public List<Atividade> getAtividadesByIdUsuario(long idUser) throws SQLException {
      AtividadeFilter filtro = new AtividadeFilter();
      filtro.setIdUsuarioIgualA(idUser);
      closeConnection();
      return getAtividadesFiltradas(filtro);
   }

   public Atividade getAtividadeIniciadaEm(Date inicio) throws SQLException {
      StringBuilder sql = new StringBuilder();
      ArrayList<Object> parametros = new ArrayList<>();

      sql.append("SELECT A.*                                                      ");
      sql.append("  FROM approptime.TB_ATIVIDADE A, TB_TAREFA T                   ");
      sql.append("    INNER JOIN approptime.TB_USUARIO U ON (T.ID_USUARIO = U.ID) ");
      sql.append("  WHERE 1=1                                                     ");

      if (Objects.nonNull(inicio)) {
         sql.append("  AND A.DT_INICIO = ? ");
         Timestamp dataSql = new java.sql.Timestamp(inicio.getTime());
         parametros.add(dataSql);
      }

      ResultSet rs = executeQuery(sql.toString(), parametros.toArray());

      Atividade toReturn = null;
      if (rs.next()) {
         toReturn = populateAtividade(rs);
      }
      rs.close();
      closeConnection();

      return toReturn;
   }

   public Long getCountAtividadesFiltradas(AtividadeFilter filtro) throws SQLException{
      ResultSet rs = montaQueryAtividadesFiltradas(filtro, Boolean.TRUE);

      return rs.last() ? Long.valueOf(rs.getInt(1)) : 0;
   }

   public List<Atividade> getAtividadesFiltradas(AtividadeFilter filtro) throws SQLException{
      List<Atividade> toReturn;
      ResultSet rs = montaQueryAtividadesFiltradas(filtro, Boolean.FALSE);
      toReturn = new LinkedList<>();
      while (rs.next()) {
         toReturn.add(populateAtividade(rs));
      }

      closeConnection();
      return toReturn;
   }

   public ResultSet montaQueryAtividadesFiltradas(AtividadeFilter filtro, Boolean isCount) throws SQLException {
      StringBuilder sql = new StringBuilder();
      ArrayList<Object> parametros = new ArrayList<>();

      if(isCount){
         sql.append("SELECT count(*) FROM (");
      }

      sql.append("SELECT A.*                                                     ");
      sql.append("  FROM approptime.TB_ATIVIDADE A                               ");
      sql.append("    INNER JOIN approptime.TB_TAREFA T ON (A.ID_TAREFA = T.ID)  ");
      sql.append("  WHERE 1=1                                                    ");

      if (Objects.nonNull(filtro.getDataInicioMaiorIgualA())) {
         sql.append("  AND A.DT_INICIO >= ? ");
         java.sql.Date dataSql = new java.sql.Date(filtro.getDataInicioMaiorIgualA().getTime());
         parametros.add(dataSql);
      }

      if (Objects.nonNull(filtro.getDataTerminoMenorIgualA())) {
         sql.append("  AND A.DT_TERMINO <= ? ");
         java.sql.Timestamp dataSql = new java.sql.Timestamp(UtilMethods.getNextDay(filtro.getDataTerminoMenorIgualA()).getTime());
         parametros.add(dataSql);
      }

      if (Objects.nonNull(filtro.getDescricaoTarefaContem())) {
         sql.append("  AND lower(T.DS_TAREFA) like lower(?) ");
         parametros.add("%" + filtro.getDescricaoTarefaContem() + "%");
      }

      if (Objects.nonNull(filtro.getIdUsuarioIgualA())) {
         sql.append("  AND T.ID_USUARIO = ? ");
         parametros.add(filtro.getIdUsuarioIgualA().toString());
      }

      if (Objects.nonNull(filtro.getIdTarefaIgualA())) {
         sql.append("  AND T.ID = ? ");
         parametros.add(filtro.getIdTarefaIgualA().toString());
      }
      if(Objects.nonNull(filtro.isEmOrdemCronologica()) && filtro.isEmOrdemCronologica() == true){
         sql.append("ORDER BY A.DT_INICIO ASC");
      }
      else{
         sql.append("  ORDER BY A.DT_TERMINO DESC");
      }

      if(Objects.nonNull(filtro.getNumeroBaseParaBuscarNoBanco()) && isCount == false){
         sql.append("  LIMIT ?,").append(Constantes.MAXIMO_DE_ATIVIDADES_POR_PAGINA_TABELA);
         parametros.add((filtro.getNumeroBaseParaBuscarNoBanco()-1)*Constantes.MAXIMO_DE_ATIVIDADES_POR_PAGINA_TABELA);
      }


      if(isCount){
         sql.append(") as Total;");
      }

      ResultSet rs = executeQuery(sql.toString(), parametros.toArray());

      return rs;
   }

   public List<Atividade> getAtividadesPaginadasFiltradas(AtividadeFilter filtro) throws SQLException{
      List<Atividade> toReturn;
      ResultSet rs = montaQueryAtividadesFiltradas(filtro, Boolean.FALSE);
      toReturn = new LinkedList<>();
      while (rs.next()) {
         toReturn.add(populateAtividade(rs));
      }

      closeConnection();
      return toReturn;
   }

   /**
    * *
    * Retorna a atividade da tarefa cujo campo Termino esteja vazio (null)
    *
    * @param idUsuario
    * @return
    * @throws SQLException
    */
   public Atividade getAtividadeSemTermino(Long idUsuario) throws SQLException {
      StringBuilder sql = new StringBuilder();

      sql.append("SELECT *                                                       ");
      sql.append("  FROM approptime.TB_ATIVIDADE A                               ");
      sql.append("    INNER JOIN approptime.TB_TAREFA T ON (A.ID_TAREFA = T.ID)  ");
      sql.append("  WHERE A.DT_TERMINO IS NULL                                   ");
      sql.append("    AND T.ID_USUARIO = ?                                       ");

      ResultSet rs = executeQuery(sql.toString(), idUsuario);

      Atividade atividade = null;
      if (rs.next()) {
         atividade = populateAtividade(rs);
      }
      rs.close();
      closeConnection();
      return atividade;
   }

   public Atividade getUltimaAtividadeFinalizada(Long idUsuario) throws SQLException {
      StringBuilder query = new StringBuilder();

      query.append("      SELECT A.*                                                                         ");
      query.append("  FROM approptime.TB_ATIVIDADE A                                                         ");
      query.append("    INNER JOIN approptime.TB_TAREFA T1 ON (A.ID_TAREFA = T1.ID)                          ");
      query.append("  WHERE T1.ID_USUARIO = ?                                                                ");
      query.append("    AND A.DT_TERMINO = (                                                                 ");
                                     /*Buscando a maior adta de teŕmino deste usuário*/
      query.append("                        SELECT MAX(A2.DT_TERMINO)                                        ");
      query.append("                          FROM approptime.TB_ATIVIDADE A2                                ");
      query.append("                            INNER JOIN approptime.TB_TAREFA T2 ON (A2.ID_TAREFA = T2.ID) ");
      query.append("                          WHERE T2.ID_USUARIO = T1.ID_USUARIO)                           ");

      ResultSet rs = executeQuery(query.toString(), idUsuario);
      Atividade toReturn = null;
      if(rs.next()){
         toReturn = populateAtividade(rs);
      }
      rs.close();
      closeConnection();

      return toReturn;
   }

   public List<Atividade> getAtividadeEmConflito(Atividade atividade) throws SQLException {
      java.sql.Timestamp dataInicioSql = new java.sql.Timestamp(atividade.getInicio().getTime());
      dataInicioSql.setTime(atividade.getInicio().getTime());

      java.sql.Timestamp dataTerminoSql = null;
      if(Objects.nonNull(atividade.getTermino())){
         dataTerminoSql = new java.sql.Timestamp(atividade.getTermino().getTime());
         dataTerminoSql.setTime(atividade.getTermino().getTime());
      }

      ArrayList<Object> parametros = new ArrayList<>();
      StringBuilder query = new StringBuilder();

      query.append("SELECT A.*                                                    ");
      query.append("  FROM approptime.TB_ATIVIDADE A                              ");
      query.append("    INNER JOIN approptime.TB_TAREFA T ON (A.ID_TAREFA = T.ID) ");
      query.append("  WHERE 1 = 1                                                 ");

      if (Objects.nonNull(atividade)) {
         //Atividade nao deve ter conflito consigo mesma (Para casos ao Salvar e sair)
         query.append("   AND A.ID <> ? ");
         parametros.add(atividade.getId());
         //Atividade só pode conflitar com outras atividades do mesmo usuario
         query.append("   AND T.ID_USUARIO = ?");
         parametros.add(atividade.getTarefa().getUser().getId());
      }

      //Verificando se as datas colidem: Verifico se elas se interceptam ou
      //se uma contém ou está contida na outra. Qualquer desses casos é considerado conflito.
      //Obs 1: As datas de término de um período A e início de um período B podem coincidir. Isso não será considerado um conflito
      //Obs 2: As datas de início de um período A e término de um período B podem coincidir. Isso não será considerado um conflito
      if (Objects.nonNull(dataInicioSql)) {
         if (Objects.nonNull(dataTerminoSql)) {
            query.append("   AND NOT ( A.DT_TERMINO <= ? OR A.DT_INICIO >= ?)");
            parametros.add(dataInicioSql);
            parametros.add(dataTerminoSql);
         } else {
            query.append("   AND NOT A.DT_TERMINO <= ?");
            parametros.add(dataInicioSql);
         }
      } else if (Objects.nonNull(dataTerminoSql)) {
         query.append("   AND NOT A.DT_INICIO >= ?");
         parametros.add(dataTerminoSql);
      }

      ResultSet rs = executeQuery(query.toString(), parametros.toArray());
      List<Atividade> toReturn = new LinkedList<>();

      while (rs.next()) {
         toReturn.add(populateAtividade(rs));
      }

      rs.close();
      closeConnection();
      return toReturn;
   }

   public List<Atividade> getAtividadeByTarefa(Tarefa tarefaBuscada) throws SQLException {
      List<Atividade> toReturn = new LinkedList<>();
      StringBuilder sql = new StringBuilder();
      ArrayList<Object> parametros = new ArrayList<>();

      sql.append("SELECT A.*                      ");
      sql.append(" FROM approptime.TB_ATIVIDADE A ");
      sql.append("   WHERE ID_TAREFA = (SELECT ID ");
      sql.append("     FROM approptime.TB_TAREFA  ");
      sql.append("         WHERE 1 = 1            ");

      if (Objects.nonNull(tarefaBuscada.getDescricao())) {
         sql.append("       AND DS_TAREFA = ?    ");
         parametros.add(tarefaBuscada.getDescricao());
      }
      if (Objects.nonNull(tarefaBuscada.getId())) {
         sql.append("       AND ID = ?           ");
         parametros.add(tarefaBuscada.getId());
      }
      if (Objects.nonNull(tarefaBuscada.getUser().getId())) {
         sql.append("       AND ID_USUARIO = ?   ");
         parametros.add(tarefaBuscada.getUser().getId());
      }
      sql.append(")                               ");

      ResultSet rs = executeQuery(sql.toString(), parametros.toArray());

      while (rs.next()) {
         toReturn.add(populateAtividade(rs));
      }
      rs.close();
      closeConnection();
      return toReturn;
   }

   public int getTotalDeAtividades(Long idUsuario) throws SQLException{
      StringBuilder sql = new StringBuilder();
      int toReturn = 15;
      sql.append("SELECT COUNT(*) AS QTD_ATIVIDADES                                ");
      sql.append("  FROM approptime.TB_TAREFA T                                    ");
      sql.append("    INNER JOIN approptime.TB_ATIVIDADE A ON (A.ID_TAREFA = T.ID) ");
      sql.append("  WHERE T.ID_USUARIO = ?                                         ");
      ResultSet rs = executeQuery(sql.toString(), idUsuario);
      if(rs.next()){
         toReturn = rs.getInt(1);
      }

      return toReturn;
   }

   public List<String> getObservacoesAtividadesByTarefa(Long idUsuario, String ds_tarefa, String ds_observacoes) throws SQLException{
      StringBuilder sql = new StringBuilder();
      ArrayList<Object> parametros = new ArrayList<>();
      sql.append("SELECT DISTINCT A.DS_OBSERVACOES                               ");
      sql.append("   FROM approptime.TB_ATIVIDADE A                              ");
      sql.append("     INNER JOIN approptime.TB_TAREFA T ON (A.ID_TAREFA = T.ID) ");
      sql.append("   WHERE 1 = 1                                                 ");
      sql.append("     AND T.ID_USUARIO = ?                                      ");
      sql.append("     AND LOWER(T.DS_TAREFA) LIKE LOWER(?)                      ");
      sql.append("     AND LOWER(A.DS_OBSERVACOES) LIKE LOWER(?)                 ");
      parametros.add(idUsuario);
      parametros.add("%"+ds_tarefa+"%");
      parametros.add("%"+ds_observacoes+"%");

      if(Objects.nonNull(ds_observacoes)){
      }

      List<String> toReturn = new ArrayList<>();
      ResultSet rs = executeQuery(sql.toString(), parametros.toArray());
      while(rs.next()){
         toReturn.add(rs.getString("DS_OBSERVACOES"));
      }

      return toReturn;
   }


   public List<AgregadoDto> getAgregadoDiario(AtividadeFilter filtro) throws SQLException{
      ResultSet rs = montaQueryAgregadoDiario(filtro, false);
      List<AgregadoDto> agregados = new ArrayList<>();
      while(rs.next()){
         agregados.add(populateAgregado(rs, Constantes.AGREGADOR_DIARIO));
      }

      return agregados;
   }

   public Long getCountAgregadoDiario(AtividadeFilter filtro) throws SQLException{
      ResultSet rs = montaQueryAgregadoDiario(filtro, Boolean.TRUE);

      return rs.last() ? Long.valueOf(rs.getInt(1)) : 0;
   }

   private ResultSet montaQueryAgregadoDiario(AtividadeFilter filtro, boolean isCount) throws SQLException {
      StringBuilder sql = new StringBuilder();
      ArrayList<Object> parametros = new ArrayList<>();

      if(isCount){
         sql.append("SELECT count(*) FROM (");
      }

      sql.append("select r.tarefa,                                                                  ");
      sql.append("           r.dia as agregador,                                                    ");
      sql.append("            periodo as totalMinutos                                               ");
      sql.append("      from (                                                                      ");
      sql.append("        select t.DS_TAREFA as tarefa,                                             ");
      sql.append("               DATE(a.DT_INICIO) as dia,                                          ");
      sql.append("               sum(timestampdiff(MINUTE, a.DT_INICIO, a.DT_TERMINO)) as periodo   ");
      sql.append("          from TB_ATIVIDADE a                                                     ");
      sql.append("            inner join TB_TAREFA t on (a.ID_TAREFA=t.ID)                          ");
      sql.append("          where t.ID_USUARIO = ?                                                  ");
      parametros.add(filtro.getIdUsuarioIgualA());
      if(Objects.nonNull(filtro.getDataInicioMaiorIgualA())){
         sql.append("            AND DATE(a.DT_INICIO) >= ?                                         ");
         parametros.add(filtro.getDataInicioMaiorIgualA());
      }
      if(Objects.nonNull(filtro.getDataTerminoMenorIgualA())){
         sql.append("            AND DATE(a.DT_TERMINO) <= ?                                        ");
         parametros.add(filtro.getDataTerminoMenorIgualA());
      }
      if(Objects.nonNull(filtro.getDescricaoTarefaContem())){
         sql.append("            AND t.DS_TAREFA LIKE ?                                             ");
         parametros.add("%"+filtro.getDescricaoTarefaContem()+"%");
      }
      sql.append("          GROUP BY t.ID, dia                                                      ");
      sql.append("      ) as r                                                                      ");
      sql.append("      order by 2 desc, 1                                                          ");

      if(Objects.nonNull(filtro.getNumeroBaseParaBuscarNoBanco()) && isCount == false){
         sql.append("  LIMIT ?,").append(Constantes.MAXIMO_DE_ATIVIDADES_POR_PAGINA_TABELA);
         parametros.add((filtro.getNumeroBaseParaBuscarNoBanco()-1)*Constantes.MAXIMO_DE_ATIVIDADES_POR_PAGINA_TABELA);
      }

      if(isCount){
         sql.append(") as Total;");
      }

      ResultSet rs = executeQuery(sql.toString(), parametros.toArray());
      return rs;
   }

   public List<AgregadoDto> getAgregadoSemanal(AtividadeFilter filtro) throws SQLException{
      ResultSet rs = montaQueryAgregadoSemanal(filtro, false);
      List<AgregadoDto> agregados = new ArrayList<>();
      while(rs.next()){
         agregados.add(populateAgregado(rs, Constantes.AGREGADOR_SEMANAL));
      }

      return agregados;
   }

   public Long getCountAgregadoSemanal(AtividadeFilter filtro) throws SQLException{
      ResultSet rs = montaQueryAgregadoSemanal(filtro, Boolean.TRUE);

      return rs.last() ? Long.valueOf(rs.getInt(1)) : 0;
   }

   private ResultSet montaQueryAgregadoSemanal(AtividadeFilter filtro, boolean isCount) throws SQLException {
      StringBuilder sql = new StringBuilder();
      ArrayList<Object> parametros = new ArrayList<>();

      if(isCount){
         sql.append("SELECT count(*) FROM (");
      }
      //Para o banco MySql:
      //Field %V - Week (01..53), where Sunday is the first day of the week; used with %X
      //Field %X - Year for the week where Sunday is the first day of the week, numeric, four digits; used with %V
      sql.append("select r.tarefa,                                                                  ");
      sql.append("           r.semana as agregador,                                                 ");
      sql.append("           r.ordenador as ordenador,                                              ");
      sql.append("            periodo as totalMinutos                                               ");
      sql.append("      from (                                                                      ");
      sql.append("        select t.DS_TAREFA as tarefa,                                             ");
      sql.append("               DATE_FORMAT(a.DT_INICIO,'%V/%X') as semana,                        ");
      sql.append("               DATE_FORMAT(a.DT_INICIO,'%X/%V') as ordenador,                     ");
      sql.append("               sum(timestampdiff(MINUTE, a.DT_INICIO, a.DT_TERMINO)) as periodo   ");
      sql.append("          from TB_ATIVIDADE a                                                     ");
      sql.append("            inner join TB_TAREFA t on (a.ID_TAREFA=t.ID)                          ");
      sql.append("          where t.ID_USUARIO = ?                                                  ");
      parametros.add(filtro.getIdUsuarioIgualA());
      if(Objects.nonNull(filtro.getDataInicioMaiorIgualA())){
         sql.append("            AND DATE(a.DT_INICIO) >= ?                                         ");
         parametros.add(filtro.getDataInicioMaiorIgualA());
      }
      if(Objects.nonNull(filtro.getDataTerminoMenorIgualA())){
         sql.append("            AND DATE(a.DT_TERMINO) <= ?                                        ");
         parametros.add(filtro.getDataTerminoMenorIgualA());
      }
      if(Objects.nonNull(filtro.getDescricaoTarefaContem())){
         sql.append("            AND t.DS_TAREFA LIKE ?                                             ");
         parametros.add("%"+filtro.getDescricaoTarefaContem()+"%");
      }
      sql.append("          GROUP BY t.ID, semana, ordenador                                        ");
      sql.append("      ) as r                                                                      ");
      sql.append("      order by 3 desc, 1                                                          ");

      if(Objects.nonNull(filtro.getNumeroBaseParaBuscarNoBanco()) && isCount == false){
         sql.append("  LIMIT ?,").append(Constantes.MAXIMO_DE_ATIVIDADES_POR_PAGINA_TABELA);
         parametros.add((filtro.getNumeroBaseParaBuscarNoBanco()-1)*Constantes.MAXIMO_DE_ATIVIDADES_POR_PAGINA_TABELA);
      }

      if(isCount){
         sql.append(") as Total;");
      }

      ResultSet rs = executeQuery(sql.toString(), parametros.toArray());
      return rs;
   }

   public List<AgregadoDto> getAgregadoMensal(AtividadeFilter filtro) throws SQLException{
      ResultSet rs = montaQueryAgregadoMensal(filtro, false);
      List<AgregadoDto> agregados = new ArrayList<>();
      while(rs.next()){
         agregados.add(populateAgregado(rs, Constantes.AGREGADOR_MENSAL));
      }

      return agregados;
   }
   
   public List<AgregadoDto> getRelatorioMensalByUsuario(Long idUsuario) throws SQLException{
      String query = "SELECT * FROM view_mensal WHERE usuario=?";
      ResultSet rs = executeQuery(query,idUsuario);
      
      List<AgregadoDto> agregados = new ArrayList<>();
      while(rs.next()){
         agregados.add(populateAgregado(rs, Constantes.AGREGADOR_MENSAL));
      }

      return agregados;
   }

   public Long getCountAgregadoMensal(AtividadeFilter filtro) throws SQLException{
      ResultSet rs = montaQueryAgregadoMensal(filtro, Boolean.TRUE);

      return rs.last() ? Long.valueOf(rs.getInt(1)) : 0;
   }

   private ResultSet montaQueryAgregadoMensal(AtividadeFilter filtro, boolean isCount) throws SQLException {
      StringBuilder sql = new StringBuilder();
      ArrayList<Object> parametros = new ArrayList<>();

      if(isCount){
         sql.append("Select count(*) from (");
      }
      sql.append("select r.tarefa,                                                                  ");
      sql.append("           r.mes as agregador,                                                    ");
      sql.append("           r.ordenador as ordenador,                                              ");
      sql.append("            periodo as totalMinutos                                               ");
      sql.append("      from (                                                                      ");
      sql.append("        select * from view_mensal                                                 ");
//      sql.append("               DATE_FORMAT(a.DT_INICIO,'%m/%Y') as mes,                           ");
//      sql.append("               DATE_FORMAT(a.DT_INICIO,'%Y/%m') as ordenador,                     ");
//      sql.append("               sum(timestampdiff(MINUTE, a.DT_INICIO, a.DT_TERMINO)) as periodo   ");
//      sql.append("          from TB_ATIVIDADE a                                                     ");
//      sql.append("            inner join TB_TAREFA t on (a.ID_TAREFA=t.ID)                          ");
      sql.append("          where usuario = ?                                                        ");
      parametros.add(filtro.getIdUsuarioIgualA());
      if(Objects.nonNull(filtro.getDataInicioMaiorIgualA())){
         sql.append("            AND DATE(a.DT_INICIO) >= ?                                          ");
         parametros.add(filtro.getDataInicioMaiorIgualA());
      }
      if(Objects.nonNull(filtro.getDataTerminoMenorIgualA())){
         sql.append("            AND DATE(a.DT_TERMINO) <= ?                                         ");
         parametros.add(filtro.getDataTerminoMenorIgualA());
      }
      if(Objects.nonNull(filtro.getDescricaoTarefaContem())){
         sql.append("            AND t.DS_TAREFA LIKE ?                                             ");
         parametros.add("%"+filtro.getDescricaoTarefaContem()+"%");
      }
      sql.append("          GROUP BY tarefa, mes, ordenador,periodo                                 ");
      sql.append("      ) as r                                                                      ");
      sql.append("      order by 3 desc, 1                                                          ");

      if(Objects.nonNull(filtro.getNumeroBaseParaBuscarNoBanco()) && isCount == false){
         sql.append("  LIMIT ?,").append(Constantes.MAXIMO_DE_ATIVIDADES_POR_PAGINA_TABELA);
         parametros.add((filtro.getNumeroBaseParaBuscarNoBanco()-1)*Constantes.MAXIMO_DE_ATIVIDADES_POR_PAGINA_TABELA);
      }

      if(isCount){
         sql.append(") as Total;");
      }

      ResultSet rs = executeQuery(sql.toString(), parametros.toArray());
      return rs;
   }

   public List<AgregadoDto> getAgregadoAnual(AtividadeFilter filtro) throws SQLException{
      ResultSet rs = montaQueryAgregadoAnual(filtro, false);
      List<AgregadoDto> agregados = new ArrayList<>();
      while(rs.next()){
         agregados.add(populateAgregado(rs, Constantes.AGREGADOR_ANUAL));
      }

      return agregados;
   }

   public Long getCountAgregadoAnual(AtividadeFilter filtro) throws SQLException{
      ResultSet rs = montaQueryAgregadoAnual(filtro, Boolean.TRUE);

      return rs.last() ? Long.valueOf(rs.getInt(1)) : 0;
   }

   private ResultSet montaQueryAgregadoAnual(AtividadeFilter filtro, boolean isCount) throws SQLException {
      StringBuilder sql = new StringBuilder();
      ArrayList<Object> parametros = new ArrayList<>();

      if(isCount){
         sql.append("select count(*) from (");
      }

      sql.append("select r.tarefa,                                                                  ");
      sql.append("       r.ano as agregador,                                                        ");
      sql.append("       periodo as totalMinutos                                                    ");
      sql.append("      from (                                                                      ");
      sql.append("        select t.DS_TAREFA as tarefa,                                             ");
      sql.append("               YEAR(a.DT_INICIO) as ano,                                          ");
      sql.append("               sum(timestampdiff(MINUTE, a.DT_INICIO, a.DT_TERMINO)) as periodo   ");
      sql.append("          from TB_ATIVIDADE a                                                     ");
      sql.append("            inner join TB_TAREFA t on (a.ID_TAREFA=t.ID)                          ");
      sql.append("          where t.ID_USUARIO = ?                                                  ");
      parametros.add(filtro.getIdUsuarioIgualA());
      if(Objects.nonNull(filtro.getDataInicioMaiorIgualA())){
         sql.append("            AND DATE(a.DT_INICIO) >= ?                                         ");
         parametros.add(filtro.getDataInicioMaiorIgualA());
      }
      if(Objects.nonNull(filtro.getDataTerminoMenorIgualA())){
         sql.append("            AND DATE(a.DT_TERMINO) <= ?                                        ");
         parametros.add(filtro.getDataTerminoMenorIgualA());
      }
      if(Objects.nonNull(filtro.getDescricaoTarefaContem())){
         sql.append("            AND t.DS_TAREFA LIKE ?                                             ");
         parametros.add("%"+filtro.getDescricaoTarefaContem()+"%");
      }
      sql.append("          GROUP BY t.ID, ano                                                      ");
      sql.append("      ) as r                                                                      ");
      sql.append("      order by 2 desc, 1                                                          ");

      if(Objects.nonNull(filtro.getNumeroBaseParaBuscarNoBanco()) && isCount == false){
         sql.append("  LIMIT ?,").append(Constantes.MAXIMO_DE_ATIVIDADES_POR_PAGINA_TABELA);
         parametros.add((filtro.getNumeroBaseParaBuscarNoBanco()-1)*Constantes.MAXIMO_DE_ATIVIDADES_POR_PAGINA_TABELA);
      }

      if(isCount){
         sql.append(") as Total;");
      }

      ResultSet rs = executeQuery(sql.toString(), parametros.toArray());
      return rs;
   }

   public List<AgregadoDto> getAgregadoGlobal(AtividadeFilter filtro) throws SQLException{
      ResultSet rs = montaQueryAgregadoGlobal(filtro, false);
      List<AgregadoDto> agregados = new ArrayList<>();
      while(rs.next()){
         agregados.add(populateAgregado(rs, Constantes.AGREGADOR_GLOBAL));
      }

      return agregados;
   }

   public Long getCountAgregadoGlobal(AtividadeFilter filtro) throws SQLException{
      ResultSet rs = montaQueryAgregadoGlobal(filtro, Boolean.TRUE);

      return rs.last() ? Long.valueOf(rs.getInt(1)) : 0;
   }

   private ResultSet montaQueryAgregadoGlobal(AtividadeFilter filtro, boolean isCount) throws SQLException {
      StringBuilder sql = new StringBuilder();
      ArrayList<Object> parametros = new ArrayList<>();

      if(isCount){
         sql.append("select count(*) from (");
      }

      sql.append("select r.tarefa,                                                                  ");
      sql.append("       '' as agregador,                                                           ");
      sql.append("        periodo as totalMinutos                                                   ");
      sql.append("      from (                                                                      ");
      sql.append("        select t.DS_TAREFA as tarefa,                                             ");
      sql.append("               sum(timestampdiff(MINUTE, a.DT_INICIO, a.DT_TERMINO)) as periodo   ");
      sql.append("          from TB_ATIVIDADE a                                                     ");
      sql.append("            inner join TB_TAREFA t on (a.ID_TAREFA=t.ID)                          ");
      sql.append("          where t.ID_USUARIO = ?                                                  ");
      parametros.add(filtro.getIdUsuarioIgualA());
      if(Objects.nonNull(filtro.getDataInicioMaiorIgualA())){
         sql.append("            AND DATE(a.DT_INICIO) >= ?                                         ");
         parametros.add(filtro.getDataInicioMaiorIgualA());
      }
      if(Objects.nonNull(filtro.getDataTerminoMenorIgualA())){
         sql.append("            AND DATE(a.DT_TERMINO) <= ?                                        ");
         parametros.add(filtro.getDataTerminoMenorIgualA());
      }
      if(Objects.nonNull(filtro.getDescricaoTarefaContem())){
         sql.append("            AND t.DS_TAREFA LIKE ?                                             ");
         parametros.add("%"+filtro.getDescricaoTarefaContem()+"%");
      }
      sql.append("          GROUP BY t.ID                                                           ");
      sql.append("      ) as r                                                                      ");
      sql.append("      order by 2 desc, 1                                                          ");

      if(Objects.nonNull(filtro.getNumeroBaseParaBuscarNoBanco()) && isCount == false){
         sql.append("  LIMIT ?,").append(Constantes.MAXIMO_DE_ATIVIDADES_POR_PAGINA_TABELA);
         parametros.add((filtro.getNumeroBaseParaBuscarNoBanco()-1)*Constantes.MAXIMO_DE_ATIVIDADES_POR_PAGINA_TABELA);
      }

      if(isCount){
         sql.append(") as Total;");
      }

      ResultSet rs = executeQuery(sql.toString(), parametros.toArray());
      return rs;
   }

   public static AgregadoDto populateAgregado(ResultSet rs, String tipo) throws SQLException {
      AgregadoDto toReturn = new AgregadoDto();
      toReturn.setAgregador(rs.getString("agregador"));
      toReturn.setDsTarefa(rs.getString("tarefa"));
      toReturn.setTipo(tipo);
      toReturn.setTotalMinutos(rs.getLong("totalMinutos"));

      return toReturn;
   }

   public static Atividade populateAtividade(ResultSet rs) throws SQLException {
      final TarefaDao tarefaDao = new TarefaDao();
      Atividade toReturn = new Atividade();
      toReturn.setId(rs.getLong("ID"));
      toReturn.setTarefa(tarefaDao.getTarefa(rs.getInt("ID_TAREFA")));
      toReturn.setInicio((Date) rs.getObject("DT_INICIO"));
      toReturn.setTermino((Date) rs.getObject("DT_TERMINO"));
      toReturn.setObservacoes(rs.getString("DS_OBSERVACOES"));

      return toReturn;
   }
}
