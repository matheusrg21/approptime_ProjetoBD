/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.dao;

import approptime.entity.Usuario;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author mrg.estagiario
 */
public class UsuarioDao extends GenericDao {

    public UsuarioDao() {
    }

    public long addUser(Usuario usr) throws SQLException{
        usr.setId(getNextId("TB_USUARIO"));
        String query = "INSERT INTO approptime.TB_USUARIO (ID, DS_NOME, DS_LOGIN, DS_PASSWORD, IMG_PERFIL) values (?, ?, ?, ?, ?)";
        executeCommand(query, usr.getId(), usr.getNome(), usr.getLogin(), usr.getPassword(), usr.getImage());
        closeConnection();
        return usr.getId();
    }

    public void removeUser(long idUser) throws SQLException{
        executeCommand("DELETE FROM approptime.TB_USUARIO WHERE ID = ?", idUser);
        closeConnection();
    }

    public void updateUser(Usuario usr) throws SQLException{
        String query = "UPDATE approptime.TB_USUARIO SET DS_NOME=?, DS_LOGIN=?, DS_PASSWORD=?, IMG_PERFIL=? WHERE ID=?";
        executeCommand(query, usr.getNome(), usr.getLogin(), usr.getPassword(), usr.getImage(), usr.getId());
        closeConnection();
    }
    
    public void updateUserCargo(Usuario usr) throws SQLException{
        String query = "UPDATE approptime.TB_USUARIO SET ID_ROLE=? WHERE ID=?";
        executeCommand(query, usr.getCargo().getId(), usr.getId());
        closeConnection();
    }
    public void updateUserDepto(Usuario usr) throws SQLException{
        String query = "UPDATE approptime.TB_USUARIO SET ID_DEPARTMENT=? WHERE ID=?";
        executeCommand(query, usr.getDepartamento().getId(), usr.getId());
        closeConnection();
    }
    
    public void updateUserTeam(Usuario usr) throws SQLException{
        String query = "UPDATE approptime.TB_USUARIO SET ID_TEAM=? WHERE ID=?";
        executeCommand(query, usr.getTime().getId(), usr.getId());
        closeConnection();
    }
    
    public void updateUserEmpresa(Usuario usr) throws SQLException{
        String query = "UPDATE approptime.TB_USUARIO SET ID_COMPANY=? WHERE ID=?";
        executeCommand(query, usr.getEmpresa().getId(), usr.getId());
        closeConnection();
    }
    

    public Usuario getUser(Long idUser) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_USUARIO WHERE ID=?", idUser);
        Usuario usr = null;
        if(rs.next()){
            usr = populateUserInfo(rs);
        }
        rs.close();
        closeConnection();
        return usr;
    }

    public List<Usuario> getAllUsers() throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_USUARIO");
        List<Usuario> toReturn = new LinkedList<>();
        while(rs.next()){
            toReturn.add(populateUserInfo(rs));
        }
        rs.close();
        closeConnection();
        return toReturn;
    }
    public Usuario getUsuarioByLogin(String login) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_USUARIO WHERE DS_LOGIN = ?", login);
        Usuario usr = null;
        if(rs.next()){
            usr = populateUserInfo(rs);
        }
        rs.close();
        return usr;
    }

    public Usuario verificaLogin(String login, String password) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_USUARIO WHERE DS_LOGIN=? AND DS_PASSWORD=?", login, password);
        Usuario usr = null;
        if(rs.next()){
            usr = populateUserInfo(rs);
        }
        rs.close();
        closeConnection();
        return usr;
    }
    
    public List<Usuario> getAllUsuarios() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_USUARIO");
        List<Usuario> toReturn = new LinkedList<>();
        while(rs.next()){
            toReturn.add(populateUserInfo(rs));
        }
        rs.close();
        return toReturn;
    }

    private static Usuario populateUserInfo(ResultSet rs) throws SQLException {
        final RoleDao roleDao = new RoleDao();
        final TeamDao teamDao = new TeamDao();
        final EmpresaDao empresaDao = new EmpresaDao();
        final DepartamentoDao departamentoDao = new DepartamentoDao();
        
        Usuario toReturn = new Usuario();
        toReturn.setId(rs.getLong("ID"));
        toReturn.setLogin(rs.getString("DS_LOGIN"));
        toReturn.setNome(rs.getString("DS_NOME"));
        toReturn.setPassword(rs.getString("DS_PASSWORD"));
        toReturn.setCargo(roleDao.getRole(rs.getLong("ID_ROLE")));
        toReturn.setEmpresa(empresaDao.getEmpresa(rs.getLong("ID_COMPANY")));
        toReturn.setDepartamento(departamentoDao.getDepartamento(rs.getLong("ID_DEPARTMENT")));
        toReturn.setTime(teamDao.getTeam(rs.getLong("ID_TEAM")));
        
        Blob blob = rs.getBlob("IMG_PERFIL");
        if(Objects.nonNull(blob)){
            toReturn.setImage(blob.getBinaryStream());
        }
        return toReturn;
    }
}
