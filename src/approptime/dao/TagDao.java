/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.dao;

import approptime.entity.Tag;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author matheus
 */
public class TagDao extends GenericDao {
    
    public TagDao() {
    }

    public long addTag(Tag tag) throws SQLException{
        tag.setId(getNextId("TB_TAG"));
        String query = "INSERT INTO approptime.TB_TAG (ID, DS_TAG, ID_USUARIO) values (?, ?, ?)";
        executeCommand(query, tag.getId(), tag.getNome(), tag.getUsuario().getId());
        closeConnection();
        return tag.getId();
    }

    public void removeTag(long idTag) throws SQLException{
        executeCommand("DELETE FROM approptime.TB_TAG WHERE ID = ?", idTag);
        closeConnection();
    }

    public void updateTag(Tag tag) throws SQLException{
        String query = "UPDATE approptime.TB_TAG SET DS_TAG=? WHERE ID=?";
        executeCommand(query, tag.getNome(), tag.getId());
        closeConnection();
    }

    public Tag getTag(int idTag) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_TAG WHERE ID=?", idTag);
        Tag tag = null;
        if(rs.next()){
            tag = populateTagInfo(rs);
        }
        rs.close();
        closeConnection();
        return tag;
    }

    public List<Tag> getAllTags() throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_TAG");
        List<Tag> toReturn = new LinkedList<>();
        while(rs.next()){
            toReturn.add(populateTagInfo(rs));
        }
        rs.close();
        closeConnection();
        return toReturn;
    }
    
    public Tag getTagByDescricao(String desc) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_TAG WHERE DS_TAG = ?", desc);
        Tag tag = null;
        if(rs.next()){
            tag = populateTagInfo(rs);
        }
        rs.close();
        return tag;
    }
    
    public List<Tag> getAllTagsByUsuario(Long idUsuarioTag) throws SQLException{
        ResultSet rs = executeQuery("SELECT * FROM approptime.TB_TAG WHERE ID_USUARIO = ?", idUsuarioTag);
        List<Tag> toReturn = new LinkedList<>();
        while(rs.next()){
            toReturn.add(populateTagInfo(rs));
        }
        rs.close();
        return toReturn;
    }

    public List<String> getDescricaoTagByUsuario(long idUsuario) throws SQLException {
      List<String> descricoesTags = new ArrayList<>();
      List<Tag> tagDescricao = getAllTagsByUsuario(idUsuario);
      tagDescricao.forEach((tag) -> {
          descricoesTags.add(tag.getNome());
        });
      closeConnection();
      return descricoesTags;
   }
    
    private static Tag populateTagInfo(ResultSet rs) throws SQLException {
        final UsuarioDao usuarioDao = new UsuarioDao();
        Tag toReturn = new Tag();
        toReturn.setId(rs.getLong("ID"));
        toReturn.setNome(rs.getString("DS_TAG"));
        toReturn.setUsuario(usuarioDao.getUser(rs.getLong("ID_USUARIO")));
        
        return toReturn;
    }
}
