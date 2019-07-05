/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.dao;

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
public class WorkspaceDao extends GenericDao {
    public WorkspaceDao() {
    }

    public long addWorkspace(Workspace workspace) throws SQLException{
        workspace.setId(getNextId("TB_WORKSPACE"));
        String query = "INSERT INTO approptime.TB_WORKSPACE (ID, DS_WORKSPACE, ID_USUARIO) values (?, ?, ?)";
        executeCommand(query, workspace.getId(), workspace.getNome(), workspace.getUsr().getId());
        closeConnection();
        return workspace.getId();
    }

    public void removeWorkspace(long idWorkspace) throws SQLException{
        executeCommand("DELETE FROM approptime.TB_WORKSPACE WHERE ID = ?", idWorkspace);
        closeConnection();
    }

    public void updateWorkspace(Workspace workspace) throws SQLException{
        String query = "UPDATE approptime.TB_WORKSPACE SET DS_WORKSPACE=? WHERE ID=?";
        executeCommand(query,  workspace.getNome(), workspace.getId());
        closeConnection();
    }

    public Workspace getWorkspace(int idWorkspace) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_WORKSPACE WHERE ID=?", idWorkspace);
        Workspace workspace = null;
        if(rs.next()){
            workspace = populateWorkspaceInfo(rs);
        }
        rs.close();
        closeConnection();
        return workspace;
    }

    public List<Workspace> getAllWorkspaces() throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_WORKSPACE");
        List<Workspace> toReturn = new LinkedList<>();
        while(rs.next()){
            toReturn.add(populateWorkspaceInfo(rs));
        }
        rs.close();
        closeConnection();
        return toReturn;
    }
    
    public List<Workspace> getAllWorkspacesByUsuario(Long idUser) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_WORKSPACE WHERE ID_USUARIO = ?", idUser);
        List<Workspace> toReturn = new LinkedList<>();
        while(rs.next()){
            toReturn.add(populateWorkspaceInfo(rs));
        }
        rs.close();
        closeConnection();
        return toReturn;
    }
   
    public Workspace getWorkspaceByDescricao(String desc) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_WORKSPACE WHERE DS_WORKSPACE = ?", desc);
        Workspace workspace = null;
        if(rs.next()){
            workspace = populateWorkspaceInfo(rs);
        }
        rs.close();
        return workspace;
    }
    
    public List<String> getDescricaoWorkspaceByUsuario(long idUsuario) throws SQLException {
      List<String> descricoesWorkspace = new ArrayList<>();
      List<Workspace> workspaceDescricao = getAllWorkspacesByUsuario(idUsuario);
      for (Workspace workspace : workspaceDescricao) {
         descricoesWorkspace.add(workspace.getNome());
      }
      closeConnection();
      return descricoesWorkspace;
   }

    private static Workspace populateWorkspaceInfo(ResultSet rs) throws SQLException {
        final UsuarioDao usuarioDao = new UsuarioDao();
        Workspace toReturn = new Workspace();
        toReturn.setId(rs.getLong("ID"));
        toReturn.setNome(rs.getString("DS_WORKSPACE"));
        toReturn.setUsr(usuarioDao.getUser(rs.getLong("ID_USUARIO")));
        
        return toReturn;
    }

}