/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.dao;


import approptime.entity.Empresa;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author matheus
 */
public class EmpresaDao extends GenericDao{
    public EmpresaDao() {
    }
    
    public long addEmpresa(Empresa empresa) throws SQLException{
        empresa.setId(getNextId("TB_COMPANY"));
        String query = "INSERT INTO approptime.TB_COMPANY (ID,DS_COMPANY) values (?, ?)";
        executeCommand(query, empresa.getId(), empresa.getNome());
        closeConnection();
        return empresa.getId();
    }

    public void removeEmpresa(long idEmpresa) throws SQLException{
        executeCommand("DELETE FROM approptime.TB_COMPANY WHERE ID = ?", idEmpresa);
        closeConnection();
    }

    public void updateEmpresa(Empresa empresa) throws SQLException{
        String query = "UPDATE approptime.TB_COMPANY SETDS_COMPANY=? WHERE ID=?";
        executeCommand(query, empresa.getNome(), empresa.getId());
        closeConnection();
    }

    public Empresa getEmpresa(Long idEmpresa) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_COMPANY WHERE ID=?", idEmpresa);
        Empresa empresa = null;
        if(rs.next()){
            empresa = populateEmpresaInfo(rs);
        }
        rs.close();
        closeConnection();
        return empresa;
    }

    public List<Empresa> getAllEmpresas() throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_COMPANY");
        List<Empresa> toReturn = new LinkedList<>();
        while(rs.next()){
            toReturn.add(populateEmpresaInfo(rs));
        }
        rs.close();
        closeConnection();
        return toReturn;
    }
    
    public Empresa getEmpresaByDescricao(String desc) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_COMPANY WHEREDS_COMPANY = ?", desc);
        Empresa empresa = null;
        if(rs.next()){
            empresa = populateEmpresaInfo(rs);
        }
        rs.close();
        return empresa;
    }

    private static Empresa populateEmpresaInfo(ResultSet rs) throws SQLException {
        Empresa toReturn = new Empresa();
        toReturn.setId(rs.getLong("ID"));
        toReturn.setNome(rs.getString("DS_TEAM"));
        
        return toReturn;
    }
}
