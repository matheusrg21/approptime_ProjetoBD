/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.dao;

import approptime.entity.Departamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author matheus
 */
public class DepartamentoDao extends GenericDao{
    
    public DepartamentoDao() {
    }
    
    public long addDepartamento(Departamento departamento) throws SQLException{
        departamento.setId(getNextId("TB_DEPARTAMENT"));
        String query = "INSERT INTO approptime.TB_DEPARTAMENT (ID, DS_DEPARTAMENT) values (?, ?)";
        executeCommand(query, departamento.getId(), departamento.getNome());
        closeConnection();
        return departamento.getId();
    }

    public void removeDepartamento(long idDepartamento) throws SQLException{
        executeCommand("DELETE FROM approptime.TB_DEPARTAMENT WHERE ID = ?", idDepartamento);
        closeConnection();
    }

    public void updateDepartamento(Departamento departamento) throws SQLException{
        String query = "UPDATE approptime.TB_DEPARTAMENT SET DS_DEPARTAMENT=? WHERE ID=?";
        executeCommand(query, departamento.getNome(), departamento.getId());
        closeConnection();
    }

    public Departamento getDepartamento(Long idDepartamento) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_DEPARTAMENT WHERE ID=?", idDepartamento);
        Departamento departamento = null;
        if(rs.next()){
            departamento = populateDepartamentoInfo(rs);
        }
        rs.close();
        closeConnection();
        return departamento;
    }

    public List<Departamento> getAllDepartamentos() throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_DEPARTAMENT");
        List<Departamento> toReturn = new LinkedList<>();
        while(rs.next()){
            toReturn.add(populateDepartamentoInfo(rs));
        }
        rs.close();
        closeConnection();
        return toReturn;
    }
    
    public Departamento getDepartamentoByDescricao(String desc) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_DEPARTAMENT WHERE DS_DEPARTAMENT = ?", desc);
        Departamento departamento = null;
        if(rs.next()){
            departamento = populateDepartamentoInfo(rs);
        }
        rs.close();
        return departamento;
    }

    private static Departamento populateDepartamentoInfo(ResultSet rs) throws SQLException {
        Departamento toReturn = new Departamento();
        toReturn.setId(rs.getLong("ID"));
        toReturn.setNome(rs.getString("DS_DEPARTAMENT"));
        
        return toReturn;
    }
}
