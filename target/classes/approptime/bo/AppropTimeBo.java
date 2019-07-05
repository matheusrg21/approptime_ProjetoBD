/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.bo;

import approptime.dao.AtividadeDao;
import approptime.dao.DepartamentoDao;
import approptime.dao.EmpresaDao;
import approptime.dao.EstruturaDao;
import approptime.dao.GenericDao;
import approptime.dao.ProjetoDao;
import approptime.dao.RoleDao;
import approptime.dao.TagDao;
import approptime.dao.TarefaDao;
import approptime.dao.TeamDao;
import approptime.dao.UsuarioDao;
import approptime.dao.WorkspaceDao;
import approptime.dao.filter.AtividadeFilter;
import approptime.dto.AgregadoDto;
import approptime.entity.Atividade;
import approptime.entity.Departamento;
import approptime.entity.Empresa;
import approptime.entity.Projeto;
import approptime.entity.Role;
import approptime.entity.Tag;
import approptime.entity.Tarefa;
import approptime.entity.Team;
import approptime.entity.Usuario;
import approptime.entity.Workspace;
import approptime.enums.TypeErro;
import approptime.frame.StatusAppropTime;
import approptime.frame.TelaPrincipal;
import approptime.util.AtualizaHoras;
import approptime.util.Constantes;
import approptime.util.UtilMethods;
import static approptime.util.UtilMethods.formataDateParaString;
import static approptime.util.UtilMethods.formataStringParaDate;
import approptime.util.ValidacaoDatas;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author mrg.estagiario
 */
public class AppropTimeBo {

   private AtualizaHoras ah;
   private Usuario user;
   private final TarefaDao tarefaDao;
   private final AtividadeDao atividadeDao;
   private final WorkspaceDao workspaceDao;
   private final ProjetoDao projetoDao;
   private final TagDao tagDao;
   private final RoleDao roleDao;
   private final DepartamentoDao deptoDao;
   private final EmpresaDao empresaDao;
   private final TeamDao timeDao;
   private final static UsuarioDao usuarioDao = new UsuarioDao();

   public AppropTimeBo() {
      this.tarefaDao = new TarefaDao();
      this.atividadeDao = new AtividadeDao();
      this.workspaceDao = new WorkspaceDao();
      this.projetoDao   = new ProjetoDao();
      this.tagDao       = new TagDao();
      this.roleDao      = new RoleDao();
      this.timeDao      = new TeamDao();
      this.empresaDao   = new EmpresaDao();
      this.deptoDao     = new DepartamentoDao();
   }
   
   public void atualizaUsuario(Usuario u){
     try {
       usuarioDao.updateUser(u);
     } catch (SQLException ex) {
       Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
     }
   }
   
   public boolean deleteUsuario(Usuario u){
     try {
       usuarioDao.removeUser(u.getId());
       return true;
     } catch (SQLException ex) {
       Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
       return false;
     }
   }

   //=============================================
   //     Métodos de manipulação de TAREFAS
   //=============================================
   public Tarefa getTarefaByDescricao(String descricao, long idUsuario) {
      try {
         return tarefaDao.getTarefaByDescricao(descricao, idUsuario);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public long adicionaTarefa(Tarefa novaTarefa) {
      try {
         return tarefaDao.addTarefa(novaTarefa);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return -1;
      }
   }

   public Tarefa getTarefaById(long id) {
      try {
         return tarefaDao.getTarefa(id);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public List<Tarefa> getTarefaByRelevancia(long idUsuario) {
      try {
         return tarefaDao.getTarefasMaisRecentes(idUsuario);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public List<String> getDescricaoTarefasByRelevancia(String descricao, Long idUsuario) {
      try {
         return tarefaDao.getDescricaoTarefasByRelevancia(idUsuario);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   //=============================================
   //     Métodos de manipulação de ATIVIDADES
   //=============================================
   public long adicionaAtividade(Atividade novaAtividade) {
      try {
         return atividadeDao.addAtividadeComTag(novaAtividade);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return -1;
      }
   }

   public Atividade getAtividadeSemTermino(Long idUsuario) {
      try {
         return atividadeDao.getAtividadeSemTermino(user.getId());
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public void atualizaAtividade(Atividade atividade) {
      try {
         atividadeDao.updateAtividade(atividade);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
   }

   public Atividade getUltimaAtividadeFinalizada(Long idUsuario) {
      try {
         return atividadeDao.getUltimaAtividadeFinalizada(idUsuario);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public List<Atividade> getAtividadeEmConflitoComAData(Atividade atividade) {
      try {
         return atividadeDao.getAtividadeEmConflito(atividade);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public List<Atividade> getAtividadesFiltradas(AtividadeFilter filtro) {
      try {
         return atividadeDao.getAtividadesFiltradas(filtro);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public List<Atividade> getAtividadesPaginadasFiltradas(AtividadeFilter filtro) {
      try {
         return atividadeDao.getAtividadesPaginadasFiltradas(filtro);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public Long getCountAtividadesFiltradas(AtividadeFilter filtro) {
      try {
         return atividadeDao.getCountAtividadesFiltradas(filtro);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public List<Atividade> getAtividadesByIdUsuario(long idUsuario) {
      try {
         return atividadeDao.getAtividadesByIdUsuario(idUsuario);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public boolean deleteAtividade(Long idAtividadeToDel) {
      try {
         Atividade atvdToDel = atividadeDao.getAtividadeById(idAtividadeToDel);
         if (atividadeDao.getAtividadeByTarefa(atvdToDel.getTarefa()).size() < 2) {
            long idTarefaToDel = atvdToDel.getTarefa().getId();
            atividadeDao.removeAtividade(idAtividadeToDel);
            tarefaDao.removeTarefa((int) idTarefaToDel);
         } else {
            atividadeDao.removeAtividade(idAtividadeToDel);
         }
         return true;
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return false;
      }
   }

   public Atividade getAtividadeById(long idAtividade) {
      try {
         return atividadeDao.getAtividadeById(idAtividade);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public Atividade getAtividadeIniciadaEm(Date inicio) {
      try {
         return atividadeDao.getAtividadeIniciadaEm(inicio);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public int getTotalDeAtividades(Long idUsuario) {
      try {
         return atividadeDao.getTotalDeAtividades(idUsuario);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return -1;
      }
   }

   public List<String> getObservacoesAtividadesByTarefa(Long idUsuario, String ds_tarefa, String ds_observacoes) {
      try {
         return atividadeDao.getObservacoesAtividadesByTarefa(idUsuario, ds_tarefa, ds_observacoes);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public List<AgregadoDto> getAgregadoDiario(AtividadeFilter filtro) {
      try {
         return atividadeDao.getAgregadoDiario(filtro);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public Long getCountAgregadoDiario(AtividadeFilter filtro) {
      try {
         return atividadeDao.getCountAgregadoDiario(filtro);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public List<AgregadoDto> getAgregadoSemanal(AtividadeFilter filtro) {
      try {
         return atividadeDao.getAgregadoSemanal(filtro);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public Long getCountAgregadoSemanal(AtividadeFilter filtro) {
      try {
         return atividadeDao.getCountAgregadoSemanal(filtro);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public List<AgregadoDto> getAgregadoMensal(AtividadeFilter filtro) {
      try {
         return atividadeDao.getAgregadoMensal(filtro);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public Long getCountAgregadoMensal(AtividadeFilter filtro) {
      try {
         return atividadeDao.getCountAgregadoMensal(filtro);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public List<AgregadoDto> getAgregadoAnual(AtividadeFilter filtro) {
      try {
         return atividadeDao.getAgregadoAnual(filtro);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public Long getCountAgregadoAnual(AtividadeFilter filtro) {
      try {
         return atividadeDao.getCountAgregadoAnual(filtro);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public List<AgregadoDto> getAgregadoGlobal(AtividadeFilter filtro) {
      try {
         return atividadeDao.getAgregadoGlobal(filtro);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   public Long getCountAgregadoGlobal(AtividadeFilter filtro) {
      try {
         return atividadeDao.getCountAgregadoGlobal(filtro);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   //=============================================
   //     Métodos de manipulação de USUARIO
   //=============================================
   public Usuario verificaLogin(String login, String senha) throws SQLException {
      this.user = usuarioDao.verificaLogin(login, senha);
      return this.user;

   }

   public long adicionaUsuario(Usuario novoUsuario) {
      try {
         return usuarioDao.addUser(novoUsuario);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
      return -1;
   }

   public Usuario getUsuarioById(Long idUsuario) {
      try {
         return usuarioDao.getUser(idUsuario);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
      return null;
   }

   public Usuario getUsuarioByLogin(String login) throws SQLException {
      return usuarioDao.getUsuarioByLogin(login);
   }
   
   public Usuario getUsuarioByEmail(String login) {
       try {
           return usuarioDao.getUsuarioByLogin(login);
       } catch (SQLException ex) {
           Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }
   
   public List<Usuario> getAllUsuarios() {
       try {
           return usuarioDao.getAllUsuarios();
       } catch (SQLException ex) {
           Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }
   
   public void atualizaUsuarioDepto(Usuario usr) {
      try {
         usuarioDao.updateUserDepto(usr);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
   }
   public void atualizaUsuarioCargo(Usuario usr) {
      try {
         usuarioDao.updateUserCargo(usr);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
   }
   public void atualizaUsuarioTeam(Usuario usr) {
      try {
         usuarioDao.updateUserTeam(usr);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
   }
   public void atualizaUsuarioEmpresa(Usuario usr) {
      try {
         usuarioDao.updateUserEmpresa(usr);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
   }

   //==============================================================
   //     Métodos para fazer o import de maneira transacionada
   //==============================================================
   private void beginTransaction() throws SQLException {
      //Criando uma conexão e compartilhando com todos os Dao's para viabilizar transação única
      usuarioDao.beginTransaction(tarefaDao, atividadeDao);
   }

   private void commitTransaction() throws SQLException {
      //Commitando transação e fechando conexão compartilhado entre os Dao's
      usuarioDao.commitTransaction();
   }

   private void rollBackTransaction() throws SQLException {
      //Cancelando transação e fechando conexão compartilhado entre os Dao's
      usuarioDao.rollbackTransaction();
   }

   public StatusAppropTime importToCsv(String path) {
      String delimitador = "\"";
      StatusAppropTime statusAppropTime = new StatusAppropTime();

      Long numeroDeRegistrosImportados = 0L;
      try {
         FileReader fr = new FileReader(path);
         BufferedReader br = new BufferedReader(fr);

         //Ignorando a primeira linha (cabeçalho)
         br.readLine();

         Usuario usuarioToPersist = null;
         Tarefa tarefaToPersist = null;
         Atividade atividadeToPersist = null;
         HashMap<Long, Usuario> mapUsuario = new HashMap<>();
         HashMap<Long, Tarefa> mapTarefa = new HashMap<>();

         beginTransaction();

         while (br.ready()) {
            String linhaCSV = br.readLine();
            numeroDeRegistrosImportados++;

            if (StringUtils.isEmpty(linhaCSV.trim())) {
               throw new RuntimeException("Linha vazia encontrada");
            }
            String[] split = linhaCSV.split(delimitador);
            while(split.length == 8){
               linhaCSV = linhaCSV.concat(br.readLine());
               split = linhaCSV.split(delimitador);
            }

            //Cria usuario, tarefa e atividade que foi lida do CSV
            Usuario usuarioCSV = Usuario.getUsuarioFromCSVLine(linhaCSV, delimitador);
            Atividade atividadeCSV = Atividade.getAtividadeFromCSVLine(linhaCSV, delimitador);
            Tarefa tarefaCSV = Tarefa.getTarefaFromCSVLine(linhaCSV, delimitador);

            //Fazendo um De-Para: vinculando o id do usuario no CSV com a respectiva entidade usuario do banco de dados
            if (mapUsuario.containsKey(usuarioCSV.getId())) {
               usuarioToPersist = mapUsuario.get(usuarioCSV.getId());
            } else {
               usuarioToPersist = getUsuarioByLogin(usuarioCSV.getLogin());
               if (Objects.isNull(usuarioToPersist)) {
                  //usuarioToPersist recebe o clone para ter os valores de usuarioCSV mas ser apontar ara uma referência diferente
                  usuarioToPersist = usuarioCSV.cloneData();
                  if (UtilMethods.isEmailValid(usuarioToPersist.getLogin()) == true) {
                     adicionaUsuario(usuarioToPersist);
                  } else {
                     throw new IllegalArgumentException("Email de usuário em formato inválido: " + usuarioCSV.getLogin());
                  }

               }
               mapUsuario.put(usuarioCSV.getId(), usuarioToPersist);
            }

            //Fazendo um De-Para: vinculando o id da tarefa no CSV com a respectiva entidade tarefa do banco de dados
            if (mapTarefa.containsKey(tarefaCSV.getId())) {
               tarefaToPersist = mapTarefa.get(tarefaCSV.getId());
            } else {
               tarefaToPersist = getTarefaByDescricao(tarefaCSV.getDescricao(), usuarioToPersist.getId());
               if (Objects.isNull(tarefaToPersist)) {
                  //tarefaToPersist recebe o clone para ter os valores de tarefaCSV mas ser apontar ara uma referência diferente
                  tarefaToPersist = tarefaCSV.cloneData();
                  tarefaToPersist.setUser(usuarioToPersist);
                  adicionaTarefa(tarefaToPersist);
               }
               mapTarefa.put(tarefaCSV.getId(), tarefaToPersist);
            }

            atividadeToPersist = atividadeCSV.cloneData();
            atividadeToPersist.setTarefa(tarefaToPersist);
            List<Atividade> atividadeEmConflitoComAData = getAtividadeEmConflitoComAData(atividadeToPersist);

            if (atividadeEmConflitoComAData.isEmpty()) {
               adicionaAtividade(atividadeToPersist);
            } else {
               statusAppropTime.setSucesso(false);
               statusAppropTime.setTypeErro(TypeErro.EXCEPTION);
               statusAppropTime.setMensagem("Colisão entre atividades");
               StringBuilder atividadesConflitantes = new StringBuilder();
               for (Atividade atividade : atividadeEmConflitoComAData) {
                  atividadesConflitantes.append("\nId da atividade: ").append(atividade.getId());
                  atividadesConflitantes.append("\nDescrição da tarefa: ").append(atividade.getTarefa().getDescricao());
                  atividadesConflitantes.append("\nInício da atividade: ").append(UtilMethods.formataDateParaString(atividade.getInicio(), "dd/MM/yyyy HH:mm:ss"));
                  atividadesConflitantes.append("\nTérmino da atividade: ").append(UtilMethods.formataDateParaString(atividade.getTermino(), "dd/MM/yyyy HH:mm:ss"));
                  atividadesConflitantes.append("\nLogin do usuario da atividade: ").append(atividade.getTarefa().getUser().getLogin());
               }
               throw new RuntimeException("Colisão de atividades detectada: " + atividadesConflitantes);

            }
         }
         commitTransaction();

         statusAppropTime.setSucesso(true);
         statusAppropTime.setMensagem(numeroDeRegistrosImportados + " arquivos importados com sucesso");
      } catch (Exception ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
            rollBackTransaction();
            statusAppropTime.setSucesso(false);
            statusAppropTime.setTypeErro(TypeErro.EXCEPTION);
            statusAppropTime.setMensagem("Erro durante o import de arquivo CSV (linha " + (numeroDeRegistrosImportados + 1) + "):\n " + ex.getLocalizedMessage());
            return statusAppropTime;
         } catch (SQLException e) {
            if (TelaPrincipal.isDebugHabilitado()) {
               Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, e);
            }
            statusAppropTime.setSucesso(false);
            statusAppropTime.setTypeErro(TypeErro.EXCEPTION);
            statusAppropTime.setMensagem("Erro ao fazer ROLLBACK no import de arquivo CSV(linha " + (numeroDeRegistrosImportados + 1) + "):\n " + ex.getLocalizedMessage());
            return statusAppropTime;
         }
      }
      return statusAppropTime;
   }

   public StatusAppropTime exportToCsv(boolean inline, String path) {
      List<Atividade> atividades;
      StatusAppropTime status = new StatusAppropTime();
      try {
         AtividadeFilter filter = new AtividadeFilter();
         filter.setIdUsuarioIgualA(user.getId());
         filter.setEmOrdemCronologica(true);
         atividades = getAtividadesFiltradas(filter);
         String separador = ";";
         String delimitador = "\"";

         StringBuilder csv = new StringBuilder();
         csv.append(Atividade.getCsvHeader(separador));
         csv.append("\n");
         csv.append(atividades.stream()
                 .map(registro -> registro.toCsv(separador, delimitador))
                 .collect(Collectors.joining("\n"))
         );

         if (inline == false || path.isEmpty() == true) {
            File generatedCsv = new File("atividades_" + user.getLogin() + ".csv");
            PrintStream write = new PrintStream(generatedCsv);
            write.print(csv.toString().replaceAll("\\" + Constantes.SIMBOLO_NOVA_LINHA, "\n"));
            generatedCsv.createNewFile();
            status.setSucesso(true);
            status.setMensagem("Arquivo exportado para: \n" + generatedCsv.getAbsolutePath());

            return status;
         } else {
            File generatedCsv = new File(path);
            PrintStream write = new PrintStream(generatedCsv);
            write.print(csv.toString());
            generatedCsv.createNewFile();
            status.setSucesso(true);
            status.setMensagem("Arquivo exportado para: \n" + generatedCsv.getAbsolutePath());

            return status;

         }
      } catch (Exception ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }

         status.setMensagem("Não foi possível gerar o arquivo CSV!");
         status.setTypeErro(TypeErro.EXCEPTION);
         List<String> arrayList = new ArrayList<>();
         arrayList.add(ex.getMessage());
         status.setErros(arrayList);

         return status;
      }
   }

   public StatusAppropTime exportRelatorioToCSV(List<AgregadoDto> agregados, String agregador) {
      StatusAppropTime status = new StatusAppropTime();
      String separador = ";";
      String delimitador = "\"";
      StringBuilder csv = new StringBuilder();

      try {
         csv.append(AgregadoDto.getCsvHeader(separador, agregador));
         csv.append("\n");
         csv.append(agregados.stream()
                 .map(registro -> registro.toCsv(separador, delimitador, agregador))
                 .collect(Collectors.joining("\n"))
         );
         File generatedCsv = new File("relatorio" + agregador +"_" + user.getLogin() + ".csv");
         PrintStream write = new PrintStream(generatedCsv);
         write.print(csv.toString());
         generatedCsv.createNewFile();
         status.setSucesso(true);
         status.setMensagem("Arquivo exportado para: \n" + generatedCsv.getAbsolutePath());

         return status;
      } catch (Exception ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         status.setMensagem("Não foi possível gerar o arquivo CSV!");
         status.setTypeErro(TypeErro.EXCEPTION);
         List<String> arrayList = new ArrayList<>();
         arrayList.add(ex.getMessage());
         status.setErros(arrayList);

         return status;
      }
   }

   public static void criarBanco() {
      Scanner read = new Scanner(System.in);

      System.out.println(TelaPrincipal.mensagemConsole(Constantes.ANSI_YELLOW + "O banco usado pela aplicação ainda não existe." + Constantes.ANSI_RESET));
      System.out.println(TelaPrincipal.mensagemConsole(Constantes.ANSI_GREEN + "1)Deseja deixar a aplicação criar o banco para você? [recomendado]" + Constantes.ANSI_RESET));
      System.out.println(TelaPrincipal.mensagemConsole(Constantes.ANSI_GREEN + "2)Deseja criar o banco você mesmo?" + Constantes.ANSI_RESET));
      System.out.println("#?");
      String answer = read.nextLine();
      System.out.println();

      if (answer.equals("2")) {
         System.out.println(TelaPrincipal.mensagemConsole(Constantes.ANSI_GREEN + "DDL para criar banco: " + Constantes.ANSI_RESET));
         showDDL();
         System.exit(0);
      } else if (answer.equals("1")) {
         createSchema();
      } else {
         throw new IllegalArgumentException("Resposta inválida");
      }
   }

   public static AppropTimeBo conectarBanco(String urlsgbd) {
      GenericDao gd = new GenericDao();
      String[] dadosConexao = urlsgbd.split(":");
      AppropTimeBo toReturn = null;

      if (dadosConexao.length < 3) {
         throw new IllegalArgumentException("Parâmetro de conexão inválido. Veja o usage");
      }

      gd.setUser(dadosConexao[Constantes.INDICE_CAMPO_USER_URLSGBD]);
      gd.setPassword(dadosConexao[Constantes.INDICE_CAMPO_PASSWORD_URLSGBD]);
      gd.setHost(dadosConexao[Constantes.INDICE_CAMPO_HOST_URLSGBD]);
      gd.setPort(dadosConexao[Constantes.INDICE_CAMPO_PORT_URLSGBD]);
      try {
         boolean verificaDatabase = gd.verificaDatabase();
         if (verificaDatabase) {
            toReturn = new AppropTimeBo();
            return toReturn;
         } else {
            return null;
         }
      } catch (SQLException ex) {
      }
      return toReturn;
   }

   public static void createSchema() {
      EstruturaDao estruturaDao = new EstruturaDao();
      estruturaDao.createDataBase();
   }

   public static void showDDL() {
      EstruturaDao estruturaDao = new EstruturaDao();
      Integer maiorlinha = estruturaDao.getDDLBaseSistema().stream()
              .mapToInt(l -> l.length())
              .max()
              .orElse(100);

      System.out.println(StringUtils.repeat("=", maiorlinha));
      System.out.println(estruturaDao.getDDLBaseSistema().stream()
              .collect(Collectors.joining("\n")));
      System.out.println(StringUtils.repeat("=", maiorlinha));
      System.out.println("");
   }

   public static File getDDL() {
      EstruturaDao estruturaDao = new EstruturaDao();
      try {
         File ddlBaseSistema = new File(Constantes.NOME_ARQUIVO_DDL_GERADO);
         FileWriter fw = new FileWriter(ddlBaseSistema);
         BufferedWriter bw = new BufferedWriter(fw);
         bw.append(estruturaDao.getDDLBaseSistema().stream()
                 .collect(Collectors.joining("\n")));
         bw.close();
         fw.close();
         return ddlBaseSistema;
      } catch (Exception ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }

   /***
    * Retorna dados da conexão com o banco, o nome do banco, o host, o usuario,
    * nesta ordem
    * @return
    */
   public String[] getDadosDeConexao(){
      GenericDao gd = new GenericDao();
      return gd.dadosDeConexao();
   }

   public boolean verificaLockFile(File lock) {

      try {
         if (Objects.nonNull(user)) {

            if (lock.exists()) {
               return true;
            } else {
               escreveVisibleFalseNoLock(lock);
               lock.createNewFile();
               lock.deleteOnExit();
               return false;
            }
         }
      } catch (Exception ex) {
         System.err.println("Não foi possível criar/verificar o arquivo lock_" + user.getLogin());
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return false;
      }
      return false;
   }

   public StatusAppropTime verificaConflitoDataAtividade(Atividade novaAtividade) {
      StatusAppropTime statusAppropTime = new StatusAppropTime();
      statusAppropTime.setSucesso(true);
      try {
         List<Atividade> conflitos = getAtividadeEmConflitoComAData(novaAtividade);
         if (conflitos.size() > 0) {
            String erro = "Conflito de data/horário com a Atividade: "
                    + "\nID: " + conflitos.get(0).getId()
                    + "\nDescrição: " + conflitos.get(0).getTarefa().getDescricao()
                    + "\nInício em: " + UtilMethods.formataDateParaString(conflitos.get(0).getInicio(), "dd/MM/yyyy - HH:mm:ss")
                    + "\nTérmino em: " + UtilMethods.formataDateParaString(conflitos.get(0).getTermino(), "dd/MM/yyyy - HH:mm:ss");

            statusAppropTime.setSucesso(false);
            statusAppropTime.setMensagem(erro);

            return statusAppropTime;
         }
      } catch (Exception ex) {
         statusAppropTime = new StatusAppropTime();
         statusAppropTime.setSucesso(false);
         statusAppropTime.setMensagem(ex.getMessage());

         return statusAppropTime;
      }

      return statusAppropTime;
   }

   /**
    * *
    * Verifica se há data de ínicio ou de término no futuro ou se hora de
    * término é anterior à hora de início
    *
    * @param validacaoDatas
    * @return StatusAppropTime com mensagem de erro ou sucesso, tipo de erro
    * caso haja e se as datas estão válidas ou não
    */
   public StatusAppropTime validaDataHora(ValidacaoDatas validacaoDatas) {

      Date atual = new Date(System.currentTimeMillis());
      Date horaAtual = formataStringParaDate(formataDateParaString(atual, "HH:mm:ss"), "HH:mm:ss");
      Date dataAtual = formataStringParaDate(formataDateParaString(atual, "dd/MM/yyyy"), "dd/MM/yyyy");

      StatusAppropTime statusAppropTime = new StatusAppropTime();
      statusAppropTime.setSucesso(true);
      try {

         //Caso DATA inicio no futuro
         if (Objects.nonNull(validacaoDatas.getDataInicio())) {
            if (dataAtual.before(validacaoDatas.getDataInicio())) {
               statusAppropTime.setSucesso(false);
               statusAppropTime.setMensagem("A data de início está no futuro.");
               statusAppropTime.setTypeErro(TypeErro.DATA_INICIO);
            } else if (dataAtual.compareTo(validacaoDatas.getDataInicio()) == 0) {

               //Caso da HORA inicio no futuro
               if (Objects.nonNull(validacaoDatas.getHoraInicio())) {
                  if (horaAtual.before(validacaoDatas.getHoraInicio())) {
                     statusAppropTime.setSucesso(false);
                     statusAppropTime.setMensagem("A hora de início está no futuro.");
                     statusAppropTime.setTypeErro(TypeErro.HORA_INICIO);
                  }
               }
            }
         }

         //Caso DATA termino no futuro
         if (Objects.nonNull(validacaoDatas.getDataTermino())) {
            if (dataAtual.before(validacaoDatas.getDataTermino())) {
               statusAppropTime.setSucesso(false);
               statusAppropTime.setMensagem("A data de término está no futuro.");
               statusAppropTime.setTypeErro(TypeErro.DATA_TERMINO);
            } else if (dataAtual.compareTo(validacaoDatas.getDataTermino()) == 0) {

               //Caso da HORA termino no futuro
               if (Objects.nonNull(validacaoDatas.getHoraTermino())) {
                  if (horaAtual.before(validacaoDatas.getHoraTermino())) {
                     statusAppropTime.setSucesso(false);
                     statusAppropTime.setMensagem("A hora de término está no futuro.");
                     statusAppropTime.setTypeErro(TypeErro.HORA_TERMINO);
                  }
               }
            }
         }

         //Caso DATA término anterior DATA início e HORA termino anterior HORA inicio
         if (Objects.nonNull(validacaoDatas.getDataInicio()) && Objects.nonNull(validacaoDatas.getDataTermino()) && statusAppropTime.isSucesso()) {
            if (validacaoDatas.getDataInicio().after(validacaoDatas.getDataTermino())) {
               statusAppropTime.setSucesso(false);
               statusAppropTime.setMensagem("Data de término é anterior a data de início.");
               statusAppropTime.setTypeErro(TypeErro.DATA_TERMINO);
            }
            //Caso datas iguais e HORA termino anterior HORA inicio
            if (validacaoDatas.getDataInicio().equals(validacaoDatas.getDataTermino())) {
               if (Objects.nonNull(validacaoDatas.getHoraInicio()) && Objects.nonNull(validacaoDatas.getHoraTermino())) {
                  if (validacaoDatas.getHoraTermino().before(validacaoDatas.getHoraInicio())) {
                     statusAppropTime.setSucesso(false);
                     statusAppropTime.setMensagem("Hora de término é anterior a hora de início.");
                     statusAppropTime.setTypeErro(TypeErro.HORA_TERMINO);
                  }
               }
            }
            if (validacaoDatas.getDataInicio().getTime() != validacaoDatas.getDataTermino().getTime()) {
               statusAppropTime.setSucesso(false);
               statusAppropTime.setMensagem("As datas de início e de término são diferentes");
               statusAppropTime.setTypeErro(TypeErro.DATA_TERMINO);

            }
         }

         if (Objects.nonNull(validacaoDatas.getHoraInicio()) && Objects.nonNull(validacaoDatas.getHoraTermino()) && statusAppropTime.isSucesso()) {
            if (Objects.nonNull(validacaoDatas.getDataInicio()) && Objects.nonNull(validacaoDatas.getDataTermino())) {
               if (validacaoDatas.getDataInicio().after(validacaoDatas.getDataTermino())) {
                  statusAppropTime.setSucesso(false);
                  statusAppropTime.setMensagem("Data de término é anterior a data de início.");
                  statusAppropTime.setTypeErro(TypeErro.DATA_TERMINO);
               }
            }
         }

         if (Objects.isNull(validacaoDatas.getHoraTermino())) {
            statusAppropTime.setSucesso(false);
         } else if (Objects.isNull(validacaoDatas.getDataTermino())) {
            statusAppropTime.setSucesso(false);
         }

         if (statusAppropTime.isSucesso()) {
            statusAppropTime.setMensagem("");
            return statusAppropTime;
         } else if (Objects.nonNull(ah)) {
            ah.setStop(true);
            ah = new AtualizaHoras();
            ah.interrupt();
         }

         return statusAppropTime;

      } catch (Exception ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         ah.setStop(true);
         ah = new AtualizaHoras();
         ah.interrupt();
         statusAppropTime.setMensagem(ex.getMessage());
         statusAppropTime.setSucesso(false);
         statusAppropTime.setTypeErro(TypeErro.EXCEPTION);
         return statusAppropTime;
      }
   }

   /**
    * *
    * Calcula a duração entre as datas, se forem válidas. Se apenas o início
    * estiver preenchido o valor da duração é feito a partir da hora/data atual.
    *
    * @param validacaoDatas
    * @return o valor de duração calculado, em formato String. Exemplo: "12h 15m
    * 10s"
    */
   public String valorDuracaoDatas(ValidacaoDatas validacaoDatas) {
      SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

      String dataInicio = formataDateParaString(formataStringParaDate(validacaoDatas.getDataInicio() + " "
              + validacaoDatas.getHoraInicio(), "dd/MM/yyyy HH:mm:ss"),
              "dd/MM/yyyy HH:mm:ss");

      String dataTermino;

      try {
         Date d1 = null;
         Date d2 = null;

         if (Objects.isNull(validacaoDatas.getDataInicio()) || Objects.isNull(validacaoDatas.getHoraInicio())) {
            return "";
         }

         if (Objects.isNull(validacaoDatas.getDataTermino()) || Objects.isNull(validacaoDatas.getHoraTermino())) {
            String hojeDataHora = formataDateParaString(new Date(System.currentTimeMillis()), "dd/MM/yyyy HH:mm:ss");
            dataTermino = hojeDataHora;
         } else {
            dataTermino = formataDateParaString(formataStringParaDate(validacaoDatas.getDataTermino() + " "
                    + validacaoDatas.getHoraTermino(), "dd/MM/yyyy HH:mm:ss"),
                    "dd/MM/yyyy HH:mm:ss");
         }

         d1 = format.parse(dataInicio);
         d2 = format.parse(dataTermino);

         String valorDuracao = UtilMethods.duracaoDatas(d1, d2);
         if (valorDuracao.equals("")) {
            return null;
         }

         return valorDuracao;

      } catch (Exception e) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, e);
         }
         return "";
      }
   }

   public void escreveVisibleFalseNoLock(File file) {
      FileWriter fw = null;
      try {
         fw = new FileWriter(file);
         BufferedWriter bw = new BufferedWriter(fw);
         bw.write(Constantes.VISIBLE_FALSE);
         bw.close();
      } catch (IOException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
      } finally {
         try {
            fw.close();
         } catch (IOException ex) {
            if (TelaPrincipal.isDebugHabilitado()) {
               Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
      }
   }

   public void escreveVisibleTrueNoLock(File file) {
      FileWriter fw = null;
      try {
         fw = new FileWriter(file);
         BufferedWriter bw = new BufferedWriter(fw);
         bw.write(Constantes.VISIBLE_TRUE);
         bw.close();
      } catch (IOException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
      } finally {
         try {
            fw.close();
         } catch (IOException ex) {
            if (TelaPrincipal.isDebugHabilitado()) {
               Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
      }
   }
   
   public long adicionaWorkspace(Workspace wkpc){
       try {
           return workspaceDao.addWorkspace(wkpc);
       } catch (SQLException ex) {
           Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
       }
       return -1;
   }
   
   public void atualizaWorkspace(Workspace wkpc){
       try {
           workspaceDao.updateWorkspace(wkpc);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
   
   public void deleteWorkspace(long idWkpc){
       try {
           workspaceDao.removeWorkspace(idWkpc);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
   
   public Workspace getWorkspaceByDescricao(String desc){
       try {
           return workspaceDao.getWorkspaceByDescricao(desc);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
   
   public List<Workspace> getAllWorkspaces(){
       try {
           return workspaceDao.getAllWorkspaces();
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
               Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
   
    public List<Workspace> getAllWorkspacesByUsuario(Usuario user) {
        try {
           return workspaceDao.getAllWorkspacesByUsuario(user.getId());
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
               Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
    }
    
    public List<String> getDescricaoWorkspacesByUsuario(Long idUsuario) {
      try {
         return workspaceDao.getDescricaoWorkspaceByUsuario(idUsuario);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
    }
   
   public long adicionaProjeto(Projeto proj){
       try {
           return projetoDao.addProjeto(proj);
       } catch (SQLException ex) {
           Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
       }
       return -1;
   }
   
   public void atualizaProjeto(Projeto proj){
       try {
           projetoDao.updateProjeto(proj);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
   
   public void deleteProjeto(long idProj){
       try {
           projetoDao.removeProjeto(idProj);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
   
   public Projeto getProjetoByDescricao(String desc){
       try {
           return projetoDao.getProjetoByDescricao(desc);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
   
   public List<Projeto> getAllProjetos(){
       try {
           return projetoDao.getAllProjetos();
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
               Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
   
   public List<Projeto> getAllProjetosByUsuario(Usuario user) {
         try {
           return projetoDao.getAllProjetosByUsuario(user.getId());
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
               Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
    }
   
   public List<String> getDescricaoProjetosByUsuario(Long idUsuario) {
      try {
         return projetoDao.getDescricaoProjetoByUsuario(idUsuario);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
    }
   
   public long adicionaTag(Tag proj){
       try {
           return tagDao.addTag(proj);
       } catch (SQLException ex) {
           Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
       }
       return -1;
   }
   
   public void atualizaTag(Tag proj){
       try {
           tagDao.updateTag(proj);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
   
   public void deleteTag(long idProj){
       try {
           tagDao.removeTag(idProj);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
   
   public Tag getTagByDescricao(String desc){
       try {
           return tagDao.getTagByDescricao(desc);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
   
   public List<Tag> getAllTagsByUsuario(Usuario usr){
       try {
           return tagDao.getAllTagsByUsuario(usr.getId());
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
   
   public List<Tag> getAllTags(){
       try {
           return tagDao.getAllTags();
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
               Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
   
   public List<String> getDescricaoTagsByUsuario(Long idUsuario) {
      try {
         return tagDao.getDescricaoTagByUsuario(idUsuario);
      } catch (SQLException ex) {
         if (TelaPrincipal.isDebugHabilitado()) {
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
      }
   }
   
   public long adicionaCargo(Role cargo){
       try {
           return roleDao.addRole(cargo);
       } catch (SQLException ex) {
           Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
       }
       return -1;
   }
   
   public void atualizaRole(Role cargo){
       try {
           roleDao.updateRole(cargo);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
   
   public void deleteRole(long idRole){
       try {
           roleDao.removeRole(idRole);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
   
   public Role getRoleByDescricao(String desc){
       try {
           return roleDao.getRoleByDescricao(desc);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
   
   public List<Role> getAllRoles(){
       try {
           return roleDao.getAllRoles();
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
               Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
   
   public long adicionaDepto(Departamento depto){
       try {
           return deptoDao.addDepartamento(depto);
       } catch (SQLException ex) {
           Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
       }
       return -1;
   }
   
   public void deleteDepto(long idDepto){
       try {
           deptoDao.removeDepartamento(idDepto);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
   
   public Departamento getDeptoByDescricao(String desc){
       try {
           return deptoDao.getDepartamentoByDescricao(desc);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
   
   public List<Departamento> getAllDeptos(){
       try {
           return deptoDao.getAllDepartamentos();
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
               Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
   
   public long adicionaTeam(Team time){
       try {
           return timeDao.addTeam(time);
       } catch (SQLException ex) {
           Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
       }
       return -1;
   }
   
   public void deleteTeam(long idTeam){
       try {
           timeDao.removeTeam(idTeam);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
   
   public Team getTeamByDescricao(String desc){
       try {
           return timeDao.getTeamByDescricao(desc);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
   
   public List<Team> getAllTeams(){
       try {
           return timeDao.getAllTeams();
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
               Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
   
   public long adicionaEmpresa(Empresa empresa){
       try {
           return empresaDao.addEmpresa(empresa);
       } catch (SQLException ex) {
           Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
       }
       return -1;
   }
   
   public void deleteEmpresa(long idEmpresa){
       try {
           empresaDao.removeEmpresa(idEmpresa);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
   
   public Empresa getEmpresaByDescricao(String desc){
       try {
           return empresaDao.getEmpresaByDescricao(desc);
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
            Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
   
   public List<Empresa> getAllEmpresas(){
       try {
           return empresaDao.getAllEmpresas();
       } catch (SQLException ex) {
           if(TelaPrincipal.isDebugHabilitado()){
               Logger.getLogger(AppropTimeBo.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
       }
   }
      

}
