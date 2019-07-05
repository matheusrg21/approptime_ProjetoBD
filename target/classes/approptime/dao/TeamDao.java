/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.dao;


import approptime.entity.Team;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author matheus
 */
public class TeamDao extends GenericDao {
    public TeamDao() {
    }
    
    public long addTeam(Team team) throws SQLException{
        team.setId(getNextId("TB_TEAM"));
        String query = "INSERT INTO approptime.TB_TEAM (ID, DS_TEAM) values (?, ?)";
        executeCommand(query, team.getId(), team.getNome());
        closeConnection();
        return team.getId();
    }

    public void removeTeam(long idTeam) throws SQLException{
        executeCommand("DELETE FROM approptime.TB_TEAM WHERE ID = ?", idTeam);
        closeConnection();
    }

    public void updateTeam(Team team) throws SQLException{
        String query = "UPDATE approptime.TB_TEAM SET DS_TEAM=? WHERE ID=?";
        executeCommand(query, team.getNome(), team.getId());
        closeConnection();
    }

    public Team getTeam(Long idTeam) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_TEAM WHERE ID=?", idTeam);
        Team team = null;
        if(rs.next()){
            team = populateTeamInfo(rs);
        }
        rs.close();
        closeConnection();
        return team;
    }

    public List<Team> getAllTeams() throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_TEAM");
        List<Team> toReturn = new LinkedList<>();
        while(rs.next()){
            toReturn.add(populateTeamInfo(rs));
        }
        rs.close();
        closeConnection();
        return toReturn;
    }
    
    public Team getTeamByDescricao(String desc) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_TEAM WHERE DS_TEAM = ?", desc);
        Team team = null;
        if(rs.next()){
            team = populateTeamInfo(rs);
        }
        rs.close();
        return team;
    }

    private static Team populateTeamInfo(ResultSet rs) throws SQLException {
        Team toReturn = new Team();
        toReturn.setId(rs.getLong("ID"));
        toReturn.setNome(rs.getString("DS_TEAM"));
        
        return toReturn;
    }
    
}
