/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.dao;

import approptime.entity.Projeto;
import approptime.entity.Workspace;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author matheus
 */
public class ProjetoDao extends GenericDao {

    public ProjetoDao() {
    }
    
    public long addProjeto(Projeto projeto) throws SQLException{
        projeto.setId(getNextId("TB_PROJECT"));
        String query = "INSERT INTO approptime.TB_PROJECT (ID, DS_PROJECT, ID_USUARIO) values (?, ?, ?)";
        executeCommand(query, projeto.getId(), projeto.getNome(), projeto.getUsr().getId());
        closeConnection();
        return projeto.getId();
    }

    public void removeProjeto(long idProjeto) throws SQLException{
        executeCommand("DELETE FROM approptime.TB_PROJECT WHERE ID = ?", idProjeto);
        closeConnection();
    }

    public void updateProjeto(Projeto workspace) throws SQLException{
        String query = "UPDATE approptime.TB_PROJECT SET DS_PROJECT=? WHERE ID=?";
        executeCommand(query,  workspace.getNome(), workspace.getId());
        closeConnection();
    }

    public Projeto getProjeto(int idProjeto) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_PROJECT WHERE ID=?", idProjeto);
        Projeto workspace = null;
        if(rs.next()){
            workspace = populateProjetoInfo(rs);
        }
        rs.close();
        closeConnection();
        return workspace;
    }

    public List<Projeto> getAllProjetos() throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_PROJECT");
        List<Projeto> toReturn = new LinkedList<>();
        while(rs.next()){
            toReturn.add(populateProjetoInfo(rs));
        }
        rs.close();
        closeConnection();
        return toReturn;
    }
    
    public List<Projeto> getAllProjetosByUsuario(Long id) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_PROJECT");
        List<Projeto> toReturn = new LinkedList<>();
        while(rs.next()){
            toReturn.add(populateProjetoInfo(rs));
        }
        rs.close();
        closeConnection();
        return toReturn;
    }
    
    public Projeto getProjetoByDescricao(String desc) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_PROJECT WHERE DS_PROJECT = ?", desc);
        Projeto workspace = null;
        if(rs.next()){
            workspace = populateProjetoInfo(rs);
        }
        rs.close();
        return workspace;
    }
    
    
    public List<String> getDescricaoProjetoByUsuario(Long idUsuario) throws SQLException {
      List<String> descricoesProjeto = new ArrayList<>();
      List<Projeto> projetoDescricao = getAllProjetosByUsuario(idUsuario);
      projetoDescricao.forEach((proj) -> {
          descricoesProjeto.add(proj.getNome());
        });
      closeConnection();
      return descricoesProjeto;
    }


    private static Projeto populateProjetoInfo(ResultSet rs) throws SQLException {
        Projeto toReturn = new Projeto();
        toReturn.setId(rs.getLong("ID"));
        toReturn.setNome(rs.getString("DS_PROJECT"));
        
        return toReturn;
    }    
}
