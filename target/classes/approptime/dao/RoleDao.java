/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.dao;

import approptime.entity.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author matheus
 */
public class RoleDao extends GenericDao {

    public RoleDao() {
    }
    
    public long addRole(Role tag) throws SQLException{
        tag.setId(getNextId("TB_ROLE"));
        String query = "INSERT INTO approptime.TB_ROLE (ID, DS_ROLE) values (?, ?)";
        executeCommand(query, tag.getId(), tag.getNome());
        closeConnection();
        return tag.getId();
    }

    public void removeRole(long idRole) throws SQLException{
        executeCommand("DELETE FROM approptime.TB_ROLE WHERE ID = ?", idRole);
        closeConnection();
    }

    public void updateRole(Role tag) throws SQLException{
        String query = "UPDATE approptime.TB_ROLE SET DS_ROLE=? WHERE ID=?";
        executeCommand(query, tag.getNome(), tag.getId());
        closeConnection();
    }

    public Role getRole(Long idRole) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_ROLE WHERE ID=?", idRole);
        Role tag = null;
        if(rs.next()){
            tag = populateRoleInfo(rs);
        }
        rs.close();
        closeConnection();
        return tag;
    }

    public List<Role> getAllRoles() throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_ROLE");
        List<Role> toReturn = new LinkedList<>();
        while(rs.next()){
            toReturn.add(populateRoleInfo(rs));
        }
        rs.close();
        closeConnection();
        return toReturn;
    }
    
    public Role getRoleByDescricao(String desc) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_ROLE WHERE DS_ROLE = ?", desc);
        Role tag = null;
        if(rs.next()){
            tag = populateRoleInfo(rs);
        }
        rs.close();
        return tag;
    }

    private static Role populateRoleInfo(ResultSet rs) throws SQLException {
        Role toReturn = new Role();
        toReturn.setId(rs.getLong("ID"));
        toReturn.setNome(rs.getString("DS_ROLE"));
        
        return toReturn;
    }
    
}
